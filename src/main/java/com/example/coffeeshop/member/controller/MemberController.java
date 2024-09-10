package com.example.coffeeshop.member.controller;

import com.example.coffeeshop.member.domain.Member;
import com.example.coffeeshop.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원 생성
    @PostMapping
    public ResponseEntity<Member> createMember(@Valid @RequestBody Member member) {
        try {
            Member createdMember = memberService.createMember(member);
            return ResponseEntity.ok(createdMember);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);  // 메시지 대신 null로 처리하여 일관성 유지
        }
    }

    // 모든 회원 조회
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    // 특정 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        try {
            Member member = memberService.getMemberById(id);
            return ResponseEntity.ok(member);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).build();  // 메시지 대신 상태 코드만 반환
        }
    }

    // 이메일로 회원 조회
    @GetMapping("/email")
    public ResponseEntity<Member> getMemberByEmail(@RequestParam String email) {
        try {
            Member member = memberService.getMemberByEmail(email);
            return ResponseEntity.ok(member);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).build();  // 메시지 대신 상태 코드만 반환
        }
    }

    // 회원 정보 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @Valid @RequestBody Member updatedMember) {
        try {
            Member member = memberService.updateMember(id, updatedMember);
            return ResponseEntity.ok(member);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).build();  // 메시지 대신 상태 코드만 반환
        }
    }

    // 회원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        try {
            memberService.deleteMember(id);
            return ResponseEntity.noContent().build();  // 상태 코드 204 반환
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).build();  // 메시지 대신 상태 코드만 반환
        }
    }
}