package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    /*
    생성자 주입 방식 - tdd 등을 작성할 때 좋음
    @RequiredArgsConstructor를 사용하면 같은 효과를 낸다

    @Autowired//생성자가 하나만 있을 때 자동으로 주입됨
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

     */

    //회원 가입
    @Transactional //변경

    public Long join(Member member){

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //중복 회원이 있는지 조회 - 있는 경우 예외 발생
    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    //@Transactional(readOnly = true)   //조회 최적화
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }


    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }


}
