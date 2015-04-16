var app = angular.module("gamerChat", ["firebase", "luegg.directives"]);
	// Config for getting the location
	app.config(function($locationProvider) {
    	$locationProvider.html5Mode(true); 
  	});
  	// Chat Controller
	app.controller("GamerChat", function($scope, $firebaseObject, $firebaseArray, $location) {
	        // Get the ID of the game from the URL <?game= > and store in the scope.
	        $scope.gameId = $location.search().game;
	        console.log($scope.gameId);
	        var gameRef = new Firebase("https://sweltering-inferno-7806.firebaseio.com/games/"+$scope.gameId);
	        $scope.game = $firebaseObject(gameRef);
	        var descRef = new Firebase("https://sweltering-inferno-7806.firebaseio.com/descriptions/"+$scope.gameId);
	        $scope.desc = $firebaseObject(descRef);
	        var commentsRef = new Firebase("https://sweltering-inferno-7806.firebaseio.com/comments/"+$scope.gameId);
	        $scope.comments = $firebaseArray(commentsRef);
	        $scope.username = 'Guest' + Math.floor(Math.random() * 100);
	        $scope.addComment = function(e){
			  	if (e.keyCode != 13) return; //Ignore if not enter key
			  	if ($scope.newComment.length == 0) return;
			  	$scope.comments.$add({
			  		// game: $scope.game.title, No need now cos separate section for each game.
			  		from: $scope.username,
			  		body: $scope.newComment
			  	});
			  	$scope.newComment = "";
			  	// $("#chatScroller").scrollTop($("#chatScroller")[0].scrollHeight);
			  	// console.log(scrollHeight);
  	  		}
	    });
	// Games List
	app.controller("GamerChatListCtrl", function($scope,$firebaseArray) {
		var ref = new Firebase("https://sweltering-inferno-7806.firebaseio.com/games");
		$scope.games = $firebaseArray(ref);
	});