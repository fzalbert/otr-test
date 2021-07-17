package com.example.apigateawayservice.enums;

public enum RouteRules {
    FORALL(null) {
        @Override
        public String[] getPaths() {
            return new String[] {
                    "/auth/**",
                    "/client/api/account/register"
            };
        }
    },
    FOR_SUPER_ADMIN(UserType.SUPER_ADMIN) {
        @Override
        public String[] getPaths() {
            return new String[] {
                    "/client/**",
                    "/employee/**"
            };
        }
    },
    FOR_ADMIN(UserType.ADMIN) {
        @Override
        public String[] getPaths() {
            return new String[] {
                    "/client/**",
                    "/employee/**"
            };
        }
    },
    FOR_CLIENT(UserType.CLIENT) {
        @Override
        public String[] getPaths() {
            return new String[] {
                    "/client/api/clients/**"
            };
        }
    };

    private UserType role;

    RouteRules(UserType role) {
        this.role = role;
    }

    public UserType getRole() {
        return role;
    }

    public abstract String[] getPaths();
}
