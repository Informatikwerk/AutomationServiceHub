(function() {
    'use strict';

    angular
        .module('automationServiceHubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('node-meta-register', {
            parent: 'entity',
            url: '/node-meta-register',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'automationServiceHubApp.nodeMetaRegister.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/node-meta-register/node-meta-registers.html',
                    controller: 'NodeMetaRegisterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('nodeMetaRegister');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('node-meta-register-detail', {
            parent: 'node-meta-register',
            url: '/node-meta-register/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'automationServiceHubApp.nodeMetaRegister.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/node-meta-register/node-meta-register-detail.html',
                    controller: 'NodeMetaRegisterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('nodeMetaRegister');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'NodeMetaRegister', function($stateParams, NodeMetaRegister) {
                    return NodeMetaRegister.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'node-meta-register',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('node-meta-register-detail.edit', {
            parent: 'node-meta-register-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/node-meta-register/node-meta-register-dialog.html',
                    controller: 'NodeMetaRegisterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NodeMetaRegister', function(NodeMetaRegister) {
                            return NodeMetaRegister.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('node-meta-register.new', {
            parent: 'node-meta-register',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/node-meta-register/node-meta-register-dialog.html',
                    controller: 'NodeMetaRegisterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                description: null,
                                iconUrl: null,
                                label: null,
                                actionElements: null,
                                valueElements: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('node-meta-register', null, { reload: 'node-meta-register' });
                }, function() {
                    $state.go('node-meta-register');
                });
            }]
        })
        .state('node-meta-register.edit', {
            parent: 'node-meta-register',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/node-meta-register/node-meta-register-dialog.html',
                    controller: 'NodeMetaRegisterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NodeMetaRegister', function(NodeMetaRegister) {
                            return NodeMetaRegister.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('node-meta-register', null, { reload: 'node-meta-register' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('node-meta-register.delete', {
            parent: 'node-meta-register',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/node-meta-register/node-meta-register-delete-dialog.html',
                    controller: 'NodeMetaRegisterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NodeMetaRegister', function(NodeMetaRegister) {
                            return NodeMetaRegister.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('node-meta-register', null, { reload: 'node-meta-register' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
