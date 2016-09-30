(function() {
    'use strict';

    angular
        .module('chatAnalysisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('register', {
            parent: 'account',
            url: '/register',
            data: {
                authorities: [],
                pageTitle: 'Registration'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/register/register.html',
                    controller: 'RegisterController',
                    controllerAs: 'vm'
                }
            }
        }).state('services', {
            parent: 'account',
            url: '/services',
            data: {
                authorities: [],
                pageTitle: 'Services'
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/services.html',
                    controller: 'RegisterController',
                    controllerAs: 'vm'
                }
            }
        }).state('contactus', {
            parent: 'account',
            url: '/contactus',
            data: {
                authorities: [],
                pageTitle: 'Registration'
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/contact.html',
                    controller: 'RegisterController',
                    controllerAs: 'vm'
                }
            }
        }).state('faq', {
            parent: 'account',
            url: '/faq',
            data: {
                authorities: [],
                pageTitle: 'Registration'
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/faqs.html',
                    controller: 'RegisterController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
