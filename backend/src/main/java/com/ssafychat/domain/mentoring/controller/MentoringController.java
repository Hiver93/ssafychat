package com.ssafychat.domain.mentoring.controller;

import com.ssafychat.domain.member.dto.PossibleMentoringDto;
import com.ssafychat.domain.member.model.Member;
import com.ssafychat.domain.mentoring.dto.*;
import com.ssafychat.domain.mentoring.model.ApplyMentoring;
import com.ssafychat.domain.mentoring.model.Mentoring;
import com.ssafychat.domain.mentoring.service.MentoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/mentoring")
@CrossOrigin("*")
public class MentoringController {
    @Autowired
    private MentoringService mentoringService;

    @GetMapping("/")
    public ResponseEntity<?> aliveCheck() {
        return new ResponseEntity<String>("Alive", HttpStatus.OK);
    }
    @GetMapping
    public List<Mentoring> findMentoring() {
        return mentoringService.findMentoring();
    }

    @GetMapping("/weekly-rankers") // 최근 7일동안 멘토링 수가 가장 높은 3명의 정보를 반환한다.
    public ResponseEntity<?> weeklyRankers() {
        // complete_mentoring에서 mentor_id로 검색한 count 상위 3명
        mentoringService.ranking();

        return new ResponseEntity<>("weekly-rankers", HttpStatus.OK);
    }
    @GetMapping("/main-info")
    public ResponseEntity<?> mainInfo() {
        MainInfoDto mainInfoDto = mentoringService.mainInfo();
        return new ResponseEntity<>(mainInfoDto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> SearchPossibleMentoring(@RequestParam(defaultValue = "") String job, @RequestParam(defaultValue = "") String belong) {
        List<PossibleMentoringDto> possibleMentorings = mentoringService.getPossibleMentoringList(job, belong);
        return new ResponseEntity<>(possibleMentorings, HttpStatus.OK);
    }
    @PostMapping("/apply")
    public ResponseEntity<String> applyMentoring(ApplyMentoringDto applyMentoringDto, HttpServletRequest request) {

        ApplyMentoring applyMentoring = mentoringService.toEntity(applyMentoringDto);

        //applyMentoringRepository
        mentoringService.applyMentoring(applyMentoring);
        return new ResponseEntity<String>("apply", HttpStatus.CREATED);

    }
    @GetMapping("/reservation")
    public ResponseEntity<?> reservationMentoring() {
        return new ResponseEntity<String>("reservation", HttpStatus.OK);
    }

    @DeleteMapping("/cancel/appointment") // 멘토가 멘토링을 취소
    @Transactional
    public ResponseEntity<?> appointmentCancel(@RequestBody CancelReasonDto cancelReasonDto, HttpServletRequest request) {
        // 헤더에서 받은 userId가 mentoring의 mentorUid와 같을 때만 기능하게
        Member user = (Member) request.getAttribute("USER");
        System.out.println(cancelReasonDto);
        int userId = user.getUserId();
        System.out.println(userId);
        // 멘토링 테이블에서 delete하면서 멘토링 정보 반환
        Mentoring mentoring = mentoringService.deleteMentoring(cancelReasonDto.getMentoringId());
        // 멘토링 취소 테이블에 insert (멘토링 테이블 정보 + reaseon, canceler)
        mentoringService.insertCancelMentoring(userId, cancelReasonDto.getReason(), mentoring);

        return new ResponseEntity<String>("cancel/appointment", HttpStatus.OK);
    }
    @DeleteMapping("/cancel/reservation")
    public ResponseEntity<?> reservationCancel() {
        return new ResponseEntity<String>("cancel/reservation", HttpStatus.OK);
    }
    @GetMapping("/appointment") // 멘토에게 들어온 멘토링 목록, 매칭된 멘토링 목록
    public ResponseEntity<?> appointmentSearch(HttpServletRequest request) {

        // 로그인 정보에서 식별자를 받아와서
        Member user = (Member) request.getAttribute("USER");
        int userId = user.getUserId();
        System.out.println(userId);

        // 예약 정보 받아오기
        List<ApplyMentoringForMentorDto> applyList = mentoringService.getApplyMentoringListForMentor(userId);
        System.out.println("applyList 조회");
        System.out.println(applyList);

        // 매칭 정보 받아오기
        List<MatchMentoringForMentorDto> matchList = mentoringService.getMatchMentoringListForMentor(userId);
        System.out.println("matchList 조회");
        System.out.println(matchList);

        // MentoringListForMentorDto에 두 List 담아서 반환
        MentoringListForMentorDto list = MentoringListForMentorDto.builder().applys(applyList).matches(matchList).build();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PostMapping("/appointment") // apply_mentoring_id에 해당하는 멘토링 요청을 수락처리
    @Transactional
    public ResponseEntity<?> appointmentAccept(HttpServletRequest request, @RequestBody MentoringDateDto mentoringDateDto) {
        // 프론트 헤더로 userId 받아온다.
        Member user = (Member) request.getAttribute("USER");
        int userId = user.getUserId();
        System.out.println(userId);
        // 프론트 body로 apply_mentoring_id 와 time 정보 받아온다. MentoringDateDto에 둘다 있음
        int applyMentoringId = mentoringDateDto.getApplyMentoringId();
        Timestamp time = mentoringDateDto.getTime();
        // mentoring_date 테이블에서 삭제한다.
        int rowsDeleted = mentoringService.deleteMentoringDate(applyMentoringId);
        System.out.println(rowsDeleted);
        // apply_mentoring 테이블에서 삭제하면서 apply_mentoring정보 반환 받는다.
        ApplyMentoring applyMentoring = mentoringService.deleteApplyMentoring(applyMentoringId);
        System.out.println(applyMentoring);
        // mentoring 테이블에 넣는다.
        Mentoring mentoring = mentoringService.insertMentoring(userId, applyMentoring, time);

        return new ResponseEntity<>(mentoring, HttpStatus.OK);
    }
    @GetMapping("/review")
    public ResponseEntity<?> reviewRollingPaper() {
        return new ResponseEntity<String>("review", HttpStatus.OK);
    }


}