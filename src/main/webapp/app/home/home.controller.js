(function() {
    'use strict';

    angular
        .module('chatAnalysisApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'ChatSessionTrending'];

    function HomeController ($scope, Principal, LoginService, $state, ChatSessionTrending) {
        var vm = this;

        vm.trendingChatSessions = [];
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        
		ChatSessionTrending.query({
			page : 0,
			size : 20
		}, function(data) {
			for (var i = 0; i < data.length; i++) {
				vm.trendingChatSessions.push(data[i]);
			}
			console.log(vm.trendingChatSessions);
		}, function() {
			console.log('error');
		});
		
        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
        $scope.options = {
                chart: {
                    type: 'lineChart',
                    height: 350,
                    margin : {
                        top: 20,
                        right: 20,
                        bottom: 40,
                        left: 55
                    },
                    x: function(d){ return d.x; },
                    y: function(d){ return d.y; },
                    useInteractiveGuideline: true,
                    dispatch: {
                        stateChange: function(e){ console.log("stateChange"); },
                        changeState: function(e){ console.log("changeState"); },
                        tooltipShow: function(e){ console.log("tooltipShow"); },
                        tooltipHide: function(e){ console.log("tooltipHide"); }
                    },
                    xAxis: {
                        axisLabel: 'Time (hrs)'	
                    },
                    yAxis: {
                        axisLabel: 'No of Chats -->',
                        tickFormat: function(d){
                            return d;
                        },
                        axisLabelDistance: -10
                    },
                    callback: function(chart){
                        console.log("!!! lineChart callback !!!");
                    }
                },
                title: {
                    enable: false,
                    text: 'Sessions'
                }
            };

            $scope.data = dataGenerator();

            /*Random Data Generator */
            function dataGenerator() {
                var genData = [];

                //Data is represented as an array of {x,y} pairs.
                for (var i = 0; i < 100; i++) {
//                	genData.push({x: i, y: i+i});
                }

                //Line chart data should be sent as an array of series objects.
                return [
                    {
                        values: genData,      //values - represents the array of {x,y} data points
                        key: 'Hours of Day', //key  - the name of the series.
                        color: '#ff7f0e'  //color - optional: choose your own line color.
                    }
                ];
            };
    }
})();
