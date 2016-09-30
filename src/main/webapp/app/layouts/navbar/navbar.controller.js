(function() {
    'use strict';

    angular
        .module('chatAnalysisApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', '$rootScope'];

    function NavbarController ($state, Auth, Principal, ProfileService, LoginService, $rootScope) {
        var vm = this;
        vm.isNavbarCollapsed = true;
        $rootScope.isAuthenticated = Principal.isAuthenticated;
        vm.isAuthenticated = Principal.isAuthenticated;
        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
    }
})();
