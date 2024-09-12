package com.example.coffeeshop.member.service;

import com.example.coffeeshop.member.domain.Member;
import com.example.coffeeshop.member.domain.Role;
import com.example.coffeeshop.member.dto.MemberDTO;
import com.example.coffeeshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private static final String ADMIN_EMAIL = "admin@example.com"; // 관리자 이메일 고정

    // 접근 권한 검사
    private void check(Member requester, Long targetMemberId) {
        if (!requester.getEmail().equals(ADMIN_EMAIL) && !requester.getMemberId().equals(targetMemberId)) {
            throw new IllegalArgumentException("다른 회원의 정보를 조회할 권한이 없습니다.");
        }
    }

    // MemberDTO -> Member 엔티티 변환
    private Member toEntity(MemberDTO memberDTO) {
        return new Member(memberDTO.getEmail(), Role.USER);
    }

    // Member 엔티티 -> MemberDTO 변환
    private MemberDTO toDTO(Member member) {
        return new MemberDTO(member.getMemberId(), member.getEmail(), member.getRole());
    }

    // 회원 생성 - 모든 회원은 USER로 생성
    @Transactional
    public MemberDTO createMember(MemberDTO memberDTO) {
        if (memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Member member = new Member(memberDTO.getEmail(), Role.USER);
        Member savedMember = memberRepository.save(member);
        return toDTO(savedMember);
    }

    // 관리자는 모든 회원 조회 가능
    public List<MemberDTO> getAllMembers(String adminEmail) {
        if (!ADMIN_EMAIL.equals(adminEmail)) {
            throw new IllegalArgumentException("관리자 권한이 없습니다.");
        }

        List<Member> members = memberRepository.findAll();
        List<MemberDTO> memberDTOs = new ArrayList<>();
        for (Member member : members) {
            memberDTOs.add(toDTO(member));
        }
        return memberDTOs;
    }

    // 회원 조회 - 본인만 조회 가능
    public MemberDTO getMemberById(Long memberId, String requesterEmail) {
        Member requester = memberRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new NoSuchElementException("요청자 이메일을 찾을 수 없습니다."));
        check(requester, memberId);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 회원을 찾을 수 없습니다."));
        return toDTO(member);
    }

    // 회원 업데이트 - 관리자 또는 본인만 가능
    @Transactional
    public MemberDTO updateMember(Long memberId, MemberDTO updatedMemberDTO, String requesterEmail) {
        Member requester = memberRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new NoSuchElementException("요청자 이메일을 찾을 수 없습니다."));
        check(requester, memberId);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 회원을 찾을 수 없습니다."));

        // 이메일 중복인 경우
        if (!member.getEmail().equals(updatedMemberDTO.getEmail()) && memberRepository.existsByEmail(updatedMemberDTO.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        member.setEmail(updatedMemberDTO.getEmail());
        Member savedMember = memberRepository.save(member);
        return toDTO(savedMember);
    }

    // 회원 삭제 - 관리자 또는 본인만 가능
    @Transactional
    public void deleteMember(Long memberId, String requesterEmail) {
        Member requester = memberRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new NoSuchElementException("요청자 이메일을 찾을 수 없습니다."));
        check(requester, memberId);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 회원을 찾을 수 없습니다."));
        memberRepository.delete(member);
    }
}