package toy.splearn.adaptor.webapi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toy.splearn.adaptor.webapi.dto.MemberRegisterResponse;
import toy.splearn.application.member.provided.MemberRegister;
import toy.splearn.domain.member.Member;
import toy.splearn.domain.member.MemberRegisterRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApi {

    private final MemberRegister memberRegister;

    @PostMapping
    public MemberRegisterResponse register(@RequestBody @Valid MemberRegisterRequest request) {
        Member member = memberRegister.register(request);

        return MemberRegisterResponse.of(member);
    }
}
