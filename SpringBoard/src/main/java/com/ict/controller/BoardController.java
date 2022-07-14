package com.ict.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ict.persistent.AttachFileDTO;
import com.ict.persistent.BoardVO;
import com.ict.persistent.Criteria;
import com.ict.persistent.PageMaker;
import com.ict.persistent.SearchCriteria;
import com.ict.service.BoardService;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

// bean container에 넣어보세요
@Controller
@Log4j
// 주소 /board가 붙도록 처리해주세요
@RequestMapping(value="/board")
public class BoardController {

	// 컨트롤러는 ???를 호출합니다. autowired로 주입해주세요.
	@Autowired
	private BoardService service;
	
	// 파일 업로드시 보조해주는 메서드 추가
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
					
			return contentType.startsWith("image");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		log.info("날짜 갓 생성 : " + date);
		String str = sdf.format(date);
		log.info("포맷 형식이 바뀐 날짜 : " + str);
		return str.replace("-", File.separator);
	}
	
	@PreAuthorize("permitAll")
	// /board/list 주소로 게시물 전체의 목록을 표현하는 컨트롤러를 만들어주세요
	// list.jsp로 연결되면 되고, getList()메서드로 가져온 전체 글 목록을
	// 포워딩해서 화면에 뿌려주면, 글번호, 글제목, 글쓴이, 날짜, 수정날짜를 화면에 출력해줍니다.
	@RequestMapping(value="/list")
					// @RequestParam의 defaultValue를 통해 값이 안들어올때 자동으로 배정할 값을 정할수 있음
	public String getBoardlist(SearchCriteria cri, Model model) {
		if (cri.getPage() == 0) {
			cri.setPage(1);
		}
		List<BoardVO> boardList = service.getList(cri);
		model.addAttribute("boardList", boardList);
		// PageMaker 생성 및 cri주입, 그리고 바인딩해서 보내기
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalBoard(service.getBoardCount(cri));
		model.addAttribute("pageMaker", pageMaker);
		return "/board/list";
	}
	
	// 글 번호를 입력받아서(주소창에서 ?bno=번호 형식으로) 해당 글의 디테일 페이지를 보여주는
	// 로직을 완성시켜주세요
	// /board/detail.jsp입니다
	// getBoardList처럼 포워딩해서 화면에 해당 글 하나에 대한 정보만 보여주면 됩니다,
	// service, mapper쪽에도 getDetail로 메서드를 
	@PreAuthorize("permitAll")
	@GetMapping("/detail")
	public String getBoardDetail(Long bno, Model model) {
		BoardVO board = service.boardDetail(bno);
		model.addAttribute("board", board);
		return "/board/detail";
	}
	
	// 글 쓰기는 말 그대로 글을 써주는 로직인데
	// 폼으로 연결되는 페이지가 하나 있어야하고
	// 그다음 폼에서 날려주는 로직을 처리해주는 페이지가 하나 더 있어야 합니다.
	@PreAuthorize("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
	@GetMapping("/insert")
	public String insertForm() {
		return "/board/insertForm";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
	// post방식으로 /insert로 들어오는 자료를 받아  콘솔에 찍어주세요
	@PostMapping("/insert")
	public String boardInsert(BoardVO board) {
		service.insert(board);
		// redirect를 사용해야 전체 글 목록을 로딩해온 다음 화면을 열어줍니다
		// 스프링 컨트롤러에서 리다이렉트를 할 때는
		// 목적주소 앞에 redirect: 를 추가로 붙입니다.
		return "redirect:/board/list";
	}
	
	@PreAuthorize("ROLE_ADMIN")
	// 글삭제 post방식으로 처리하도록 합니다
	@PostMapping("/delete")
	public String deleteBoard(Long bno, SearchCriteria cri, RedirectAttributes rttr, BoardVO board) {
		// 삭제 후 리스트로 돌아갈 수 있도록 내부 로직을 만들어주시고
		// 디테일 페이지에 삭제요청을 넣을 수 있는 폼을 만들어주세요
		service.delete(bno);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("bno", board.getBno());
		return "redirect:/board/list";
	}
	
	@PreAuthorize("ROLE_ADMIN")
	// 글 수정 요청도 post로 받습니다
	@PostMapping("/updateForm")
	public String updateBoardForm(Long bno, Model model) {
		// 해당 bno의 글 정보만 뽑아서 저장한 다음
		// 포워딩을 이용해 updateForm.jsp에 보내줍니다.
		BoardVO board = service.boardDetail(bno);
		model.addAttribute("board", board);
		return "/board/updateForm";
	}
	
	@PreAuthorize("ROLE_ADMIN")
	@PostMapping("/update")
	public String updateBoard(BoardVO board, SearchCriteria cri, RedirectAttributes rttr) {
		service.update(board);
		
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("searchType", cri.getSearchType());
		
		return "redirect:/board/detail?bno=" + board.getBno();
	}
	
	
	@PostMapping(value="/uploadFormAction", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadFormPost(MultipartFile[] uploadFile) {
		
		List<AttachFileDTO> list = new ArrayList<>();
		
		String uploadFolder = "C:\\upload_data\\temp";
		
		String uploadFolderPath = getFolder();
		
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
			log.info("only file name : " + uploadFileName);
			
			attachDTO.setFileName(uploadFileName);
			
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				if(checkImageType(saveFile)) {
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail = new FileOutputStream(
							new File(uploadPath, "s_" + uploadFileName));
					
					Thumbnailator.createThumbnail(
							multipartFile.getInputStream(), thumbnail,100, 100);
					
					thumbnail.close();
				}
				list.add(attachDTO);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} // for문 끝나는 지점
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/display")
	@ResponseBody
	// byte 자료형인 이유는 그림정보이므로 2진수를 보내야되서
	public ResponseEntity<byte[]> getFile(String fileName) {
		log.info("fileName: " + fileName);
		
		File file = new File("c:\\upload_data\\temp\\" + fileName);
		
		log.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			// 스프링쪽 HttpHeaders import 하기 // java.net으로 임포트시 생성자가 오류남
			HttpHeaders header = new HttpHeaders();
			
			// 이 메시지를 통해서 헤더부분의 파일정보가 들어감
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@GetMapping(value="/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String fileName) {
		log.info("download file: " + fileName);
		Resource resource = new FileSystemResource("C:\\upload_data\\temp\\" + fileName);
		
		log.info("resource: " + resource);
		
		String resourceName = resource.getFilename();
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			headers.add("Content-Disposition", "attachment; filename=" + 
						new String(resourceName.getBytes("UTF-8"),"ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
				
	}
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) {
		log.info("deleteFile : " + fileName);
		
		File file = null;
		
		try {
			file = new File("C:\\upload_data\\temp\\" + URLDecoder.decode(fileName, "UTF-8"));
			
			file.delete();
			log.info("이미지 타입체크 : " + type);
			log.info("이미지 여부 : " + type.equals("image"));
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_","");
				
				log.info("largeFileName : " + largeFileName);
				
				file = new File(largeFileName);
				
				file.delete();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<String>("deleted" , HttpStatus.OK);
		
	}
	
}
