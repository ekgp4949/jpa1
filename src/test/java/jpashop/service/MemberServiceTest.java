package jpashop.service;

import jpashop.domain.Member;
import jpashop.repository.MemberRepository;
import jpashop.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입되어야 한다")
    void signUpTest() {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        assertThat(member).isEqualTo(memberRepository.findOne(saveId));
    }

    @Test
    @DisplayName("중복회원 예외가 발생해야 한다")
    void throwExceptionForDuplicatedMember() {
        //given
        Member member = new Member();
        member.setName("kim");
        Member duplicatedMember = new Member();
        duplicatedMember.setName("kim");

        //when
        memberService.join(member);
        try {
            memberService.join(duplicatedMember);
        } catch (IllegalStateException exception) {
            return;
        }

        //then
        //assertThrows(IllegalStateException.class, () -> {memberService.join(duplicatedMember);});
        Assertions.fail("예외가 발생해야 한다.");
    }

}