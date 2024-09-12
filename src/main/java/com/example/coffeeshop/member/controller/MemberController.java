package com.example.coffeeshop.member.controller;

import com.example.coffeeshop.member.dto.MemberDTO;
import com.example.coffeeshop.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 생성
    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberDTO memberDTO) {
        MemberDTO createdMember = memberService.createMember(memberDTO);
        return ResponseEntity.ok(createdMember);
    }

    // 관리자만 모든 회원 조회 가능
    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers(@RequestParam String adminEmail) {
        List<MemberDTO> members = memberService.getAllMembers(adminEmail);
        return ResponseEntity.ok(members);
    }

    // 자신의 회원 정보만 조회 가능
    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id, @RequestParam String email) {
        MemberDTO member = memberService.getMemberById(id, email);
        return ResponseEntity.ok(member);
    }

    // 회원 업데이트 - 관리자 또는 본인만 가능
    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable Long id, @Valid @RequestBody MemberDTO updatedMember, @RequestParam String email) {
        MemberDTO member = memberService.updateMember(id, updatedMember, email);
        return ResponseEntity.ok(member);
    }

    // 회원 삭제 - 관리자 또는 본인만 가능
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id, @RequestParam String email) {
        memberService.deleteMember(id, email);
        return ResponseEntity.ok().build();
    }
}