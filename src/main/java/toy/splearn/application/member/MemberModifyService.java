package toy.splearn.application.member;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import toy.splearn.application.member.provided.MemberFinder;
import toy.splearn.application.member.provided.MemberRegister;
import toy.splearn.application.member.required.EmailSender;
import toy.splearn.application.member.required.MemberRepository;
import toy.splearn.domain.member.*;
import toy.splearn.domain.shared.Email;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {

    private final MemberFinder memberFinder;
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.activate();

        return memberRepository.save(member); // todo save 메소드를 호출하는 이유
    }

    @Override
    public Member deactivate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.deactivate();

        return memberRepository.save(member); // todo save 메소드를 호출하는 이유
    }

    @Override
    public Member updateInfo(Long memberId, MemberInfoUpdateRequest infoUpdateRequest) {
        Member member = memberFinder.find(memberId);

        checkDuplicateProfile(member, infoUpdateRequest.profileAddress());

        member.updateInfo(infoUpdateRequest);

        return memberRepository.save(member); // todo save 메소드를 호출하는 이유
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        memberRepository.findByEmail(new Email(registerRequest.email()))
                .ifPresent(member -> {
                    throw new DuplicateEmailException("이미 사용중인 이메일입니다: " + registerRequest.email());
                });
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");
    }

    private void checkDuplicateProfile(Member member, String profileAddress) {
        if (profileAddress.isEmpty()) return;

        Profile currentProfile = member.getDetail().getProfile();
        if (currentProfile != null && currentProfile.address().equals(profileAddress)) return;

        if (memberRepository.findByProfile(new Profile(profileAddress)).isPresent()) {
            throw new DuplicateProfileException("이미 존재하는 프로필 주소 입니다: " + profileAddress);
        }
    }
}
