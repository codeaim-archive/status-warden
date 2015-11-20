package com.codeaim.statuswarden.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class User
{
    @Id
    private final String id;
    private final String name;
    private final String email;
    private final String resetToken;
    private final String accessToken;
    private final String passwordHash;
    private final String salt;
    private final LocalDateTime updated;
    private final LocalDateTime created;
    @Version
    private final int version;
    private final boolean admin;
    private final boolean emailVerified;

    public User(
        final String id,
        final String name,
        final String email,
        final String resetToken,
        final String accessToken,
        final String passwordHash,
        final String salt,
        final LocalDateTime updated,
        final LocalDateTime created,
        final int version,
        final boolean admin,
        final boolean emailVerified
    )
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.resetToken = resetToken;
        this.accessToken = accessToken;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.updated = updated;
        this.created = created;
        this.version = version;
        this.admin = admin;
        this.emailVerified = emailVerified;
    }

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getResetToken()
    {
        return this.resetToken;
    }

    public String getAccessToken()
    {
        return this.accessToken;
    }

    public String getPasswordHash()
    {
        return this.passwordHash;
    }

    public String getSalt()
    {
        return this.salt;
    }

    public LocalDateTime getUpdated()
    {
        return this.updated;
    }

    public LocalDateTime getCreated()
    {
        return this.created;
    }

    public int getVersion()
    {
        return this.version;
    }

    public boolean isAdmin()
    {
        return this.admin;
    }

    public boolean isEmailVerified()
    {
        return this.emailVerified;
    }

    public static Builder builder() { return new Builder(); }

    public static Builder buildFrom(User user)
    {
        return builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .resetToken(user.getResetToken())
            .accessToken(user.getAccessToken())
            .passwordHash(user.getPasswordHash())
            .salt(user.getSalt())
            .created(user.getCreated())
            .version(user.getVersion())
            .admin(user.isAdmin())
            .emailVerified(user.isEmailVerified());
    }

    public static class Builder
    {
        private String id;
        private String name;
        private String email;
        private String resetToken;
        private String accessToken;
        private String passwordHash;
        private String salt;
        private LocalDateTime created;
        private int version;
        private boolean admin;
        private boolean emailVerified;

        private Builder id(final String id)
        {
            this.id = id;
            return this;
        }

        public Builder name(final String name)
        {
            this.name = name;
            return this;
        }

        public Builder email(final String email)
        {
            this.email = email;
            return this;
        }

        public Builder resetToken(final String resetToken)
        {
            this.resetToken = resetToken;
            return this;
        }

        public Builder accessToken(final String accessToken)
        {
            this.accessToken = accessToken;
            return this;
        }

        public Builder passwordHash(final String passwordHash)
        {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder salt(final String salt)
        {
            this.salt = salt;
            return this;
        }

        public Builder created(final LocalDateTime created)
        {
            this.created = created;
            return this;
        }

        public Builder version(final int version)
        {
            this.version = version;
            return this;
        }

        public Builder admin(final boolean admin)
        {
            this.admin = admin;
            return this;
        }

        public Builder emailVerified(final boolean emailVerified)
        {
            this.emailVerified = emailVerified;
            return this;
        }

        public User build()
        {
            return new User(
                this.id,
                this.name,
                this.email,
                this.resetToken,
                this.accessToken,
                this.passwordHash,
                this.salt,
                LocalDateTime.now(ZoneOffset.UTC),
                this.created == null ? LocalDateTime.now(ZoneOffset.UTC) : this.created,
                this.version,
                this.admin,
                this.emailVerified
            );
        }
    }
}
