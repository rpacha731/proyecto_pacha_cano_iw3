package com.iua.iw3.proyecto.pacha_cano.security.authtoken;


import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import lombok.*;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "auth_token")
public class AuthToken implements Serializable {

    private static final long serialVersionUID = -2431961978969608445L;

    @Id
    private String series;

    private String token;

    @Column(columnDefinition = "DATETIME DEFAULT NULL")
    private Date toDate;

    @Column(columnDefinition = "DATETIME DEFAULT NULL")
    private Date lastUsed;

    private String username;

    private Long requestCount = 0L;

    @Transient
    private SecureRandom random = new SecureRandom();

    public void addRequest() {
        requestCount++;
    }

    public AuthToken (User user, Date toDate) {
        setUsername(user.getUsername());
        setToDate(toDate);
        setLastUsed(new Date());
        setSeries(generateSeriesData());
        setToken(generateTokenData());
    }

    public String encodeTokenValue() {
        String[] cookieTokens = new String[] { getSeries(), getToken() };
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cookieTokens.length; i++) {
            sb.append(cookieTokens[i]);

            if (i < cookieTokens.length - 1) {
                sb.append(":");
            }
        }

        String value = sb.toString();

        sb = new StringBuilder(new String(Base64.getEncoder().encode(value.getBytes())));

        while (sb.charAt(sb.length() - 1) == '=') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    private String generateSeriesData() {
        byte[] newSeries = new byte[16];
        random.nextBytes(newSeries);
        return new String(Base64.getEncoder().encode(newSeries));
    }

    private String generateTokenData() {
        byte[] newToken = new byte[16];
        random.nextBytes(newToken);
        return new String(Base64.getEncoder().encode(newToken));
    }

    @Override
    public String toString() {
        return String.format(
                "Token: serie=%s, token=%s, user=%s, tokenValue=%s, lastUsed=%s, requestCount=%s, valid=%s",
                getSeries(), getToken(), getUsername(), encodeTokenValue(), getLastUsed(), getRequestCount(), valid());
    }

    public boolean valid() {
        return System.currentTimeMillis() <= getToDate().getTime();
    }

    private static final String DELIMITER = ":";

    public static String[] decode(String token) throws InvalidCookieException {
        StringBuilder tokenBuilder = new StringBuilder(token);
        tokenBuilder.append("=".repeat(Math.max(0, tokenBuilder.length() % 4)));
        token = tokenBuilder.toString();
        try {
            String cookieAsPlainText = new String(Base64.getDecoder().decode(token.getBytes()));

            String[] tokens = StringUtils.delimitedListToStringArray(cookieAsPlainText, DELIMITER);

            if ((tokens[0].equalsIgnoreCase("http") || tokens[0].equalsIgnoreCase("https"))
                    && tokens[1].startsWith("//")) {
                String[] newTokens = new String[tokens.length - 1];
                newTokens[0] = tokens[0] + ":" + tokens[1];
                System.arraycopy(tokens, 2, newTokens, 1, newTokens.length - 1);
                tokens = newTokens;
            }

            return tokens;
        } catch (Exception e) {
            throw new InvalidParameterException("El Token no está codificado Base64; valor='" + token + "'");
        }
    }
}
