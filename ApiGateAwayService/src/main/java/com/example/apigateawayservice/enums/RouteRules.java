package com.example.apigateawayservice.enums;

public enum RouteRules {
    FOR_ALL(null) {
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
                    "/appeal/**",
                    "/appeal/api/appeals/filter-for-admin",
                    "/appeal/api/appeals/delete",
                    "/appeal/api/appeals/check",
                    "/appeal/api/reports/approve-or-reject",
                    "/appeal/api/tasks/**",
                    "/client/api/clients/**",
                    "/client/api/ban/**",
                    "/employee/**"
            };
        }
    },
    FOR_ADMIN(UserType.ADMIN) {
        @Override
        public String[] getPaths() {
            return new String[] {
                    "/appeal/**",
                    "/appeal/api/appeals/filter-for-admin",
                    "/appeal/api/appeals/delete",
                    "/appeal/api/appeals/check",
                    "/appeal/api/reports/approve-or-reject",
                    "/appeal/api/tasks/**",
                    "/client/api/clients/**",
                    "/client/api/ban/**",
                    "/employee/**"
            };
        }
    },
    FOR_CLIENT(UserType.CLIENT) {
        @Override
        public String[] getPaths() {
            return new String[] {
                    "/appeal/**",
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
