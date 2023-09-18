package ori.loproject.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ori.loproject.domain.members.MemberRole;

@NoArgsConstructor
@Data
public class MemberDto {
    private String name;
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;
    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    private String phonenum;
    private String address;
    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    private String nickname;

    @Builder
    public MemberDto(String name, String email, String password, String phonenum,
                     String address, String nickname) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phonenum = phonenum;
        this.address = address;
        this.nickname = nickname;
    }
}