(function() {
    'use strict';

    angular
        .module('chatAnalysisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('chat-session', {
            parent: 'entity',
            url: '/chat-session',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChatSessions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chat-session/chat-sessions.html',
                    controller: 'ChatSessionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        }).state('chat-session.history', {
            parent: 'entity',
            url: '/chat-session/history',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChatSessions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chat-session/chat-sessions.history.html',
                    controller: 'ChatSessionHistoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('chat-session-detail', {
            parent: 'entity',
            url: '/chat-session/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChatSession'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chat-session/chat-session-detail.html',
                    controller: 'ChatSessionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ChatSession', function($stateParams, ChatSession) {
                    return ChatSession.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'chat-session',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('chat-session-detail.edit', {
            parent: 'chat-session-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chat-session/chat-session-dialog.html',
                    controller: 'ChatSessionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChatSession', function(ChatSession) {
                            return ChatSession.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chat-session.new', {
            parent: 'chat-session',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chat-session/chat-session-dialog.html',
                    controller: 'ChatSessionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                sourceUrl: null,
                                emailFlag: null,
                                callbackFlag: null,
                                questionsCount: null,
                                answersCount: null,
                                topic: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('chat-session', null, { reload: 'chat-session' });
                }, function() {
                    $state.go('chat-session');
                });
            }]
        })
        .state('chat-session.edit', {
            parent: 'chat-session',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chat-session/chat-session-dialog.html',
                    controller: 'ChatSessionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChatSession', function(ChatSession) {
                            return ChatSession.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chat-session', null, { reload: 'chat-session' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chat-session.delete', {
            parent: 'chat-session',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chat-session/chat-session-delete-dialog.html',
                    controller: 'ChatSessionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ChatSession', function(ChatSession) {
                            return ChatSession.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chat-session', null, { reload: 'chat-session' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('historic-chat-session', {
            parent: 'entity',
            url: '/chat-session/history',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Historic Reports'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chat-session/historic-chat-sessions.html',
                    controller: 'ChatSessionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }

})();
