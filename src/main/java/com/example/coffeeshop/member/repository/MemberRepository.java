package com.example.coffeeshop.member.repository;

import com.example.coffeeshop.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일로 회원 조회
    Optional<Member> findByEmail(String email);

    // 회원 존재 여부 확인
    boolean existsByEmail(String email);
}