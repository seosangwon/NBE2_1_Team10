package com.example.coffeeshop.member.service;

import com.example.coffeeshop.member.domain.Member;
import com.example.coffeeshop.member.dto.MemberDTO;
import com.example.coffeeshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 생성
    public MemberDTO createMember(MemberDTO memberDTO) {
        if (memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Member member = toEntity(memberDTO);
        Member savedMember = memberRepository.save(member);
        return toDTO(savedMember);
    }

    // 모든 회원 조회
    public List<MemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        List<MemberDTO> memberDTOs = new ArrayList<>();

        for (Member member : members) {
            memberDTOs.add(toDTO(member));  // Member 엔티티를 DTO로 변환 후 리스트에 추가
        }

        return memberDTOs;
    }

    // 회원 조회
    public MemberDTO getMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 회원을 찾을 수 없습니다: " + memberId));
        return toDTO(member);
    }

    // 이메일로 회원 조회
    public MemberDTO getMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("해당 이메일의 회원을 찾을 수 없습니다: " + email));
        return toDTO(member);
    }

    // 회원 업데이트
    public MemberDTO updateMember(Long memberId, MemberDTO updatedMemberDTO) {
        if (memberRepository.existsByEmail(updatedMemberDTO.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 회원을 찾을 수 없습니다: " + memberId));

        member.setEmail(updatedMemberDTO.getEmail());
        Member savedMember = memberRepository.save(member);
        return toDTO(savedMember);
    }

    // 회원 삭제
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 회원을 찾을 수 없습니다: " + memberId));
        memberRepository.delete(member);
    }

    // MemberDTO -> Member 엔티티 변환
    private Member toEntity(MemberDTO memberDTO) {
        return new Member(memberDTO.getEmail());
    }

    // Member 엔티티 -> MemberDTO 변환
    private MemberDTO toDTO(Member member) {
        return new MemberDTO(member.getMemberId(), member.getEmail());
    }
}