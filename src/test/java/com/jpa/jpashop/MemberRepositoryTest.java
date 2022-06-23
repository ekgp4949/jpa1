package com.jpa.jpashop;

import com.jpa.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    void testMember() {
        Member member = new Member();
        member.setName("member");

        Long saveId = memberRepository.save(member);
        Member saveMember = memberRepository.find(saveId);

        Assertions.assertThat(saveMember).isSameAs(member);
        Assertions.assertThat(saveMember.getId()).isEqualTo(saveId);
        Assertions.assertThat(saveMember.getName()).isEqualTo("member");
    }
}