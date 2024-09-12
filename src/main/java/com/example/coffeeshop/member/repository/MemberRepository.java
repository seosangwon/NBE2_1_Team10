package com.example.coffeeshop.member.repository;

import com.example.coffeeshop.member.domain.Member;
import com.example.coffeeshop.member.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이메일로 회원 조회
    Optional<Member> findByEmail(String email);

    // 회원 존재 여부 확인
    boolean existsByEmail(String email);

    // 역할로 회원 조회
    List<Member> findByRole(Role role);
}