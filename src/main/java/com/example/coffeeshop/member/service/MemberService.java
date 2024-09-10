package com.example.coffeeshop.member.service;

import com.example.coffeeshop.member.domain.Member;
import com.example.coffeeshop.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 회원 생성
    public Member createMember(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        return memberRepository.save(member);
    }

    // 모든 회원 조회
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // 특정 회원 조회
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 회원을 찾을 수 없습니다: " + memberId));
    }

    // 이메일로 회원 조회
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("해당 이메일의 회원을 찾을 수 없습니다: " + email));
    }

    // 특정 회원 업데이트
    public Member updateMember(Long memberId, Member updatedMember) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 회원을 찾을 수 없습니다: " + memberId));

        existingMember.setEmail(updatedMember.getEmail());

        return memberRepository.save(existingMember);
    }

    // 특정 회원 삭제
    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new NoSuchElementException("해당 ID의 회원을 찾을 수 없습니다: " + memberId);
        }
        memberRepository.deleteById(memberId);
    }
}