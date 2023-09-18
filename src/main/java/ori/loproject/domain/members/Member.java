package ori.loproject.domain.members;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import ori.loproject.dto.MemberDto;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "memberDB")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberid;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String phonenum;

    @Column
    private String address;

    @Column
    private String nickname;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Builder
    public Member(String name, String email, String password, String phonenum,
                  String address, String nickname, MemberRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phonenum = phonenum;
        this.address = address;
        this.nickname = nickname;
        this.role = role;
    }

    public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        Member member = Member.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .phonenum(memberDto.getPhonenum())
                .address(memberDto.getAddress())
                .nickname(memberDto.getNickname())
                .role(MemberRole.USER)
                .build();
        return member;
    }
}

