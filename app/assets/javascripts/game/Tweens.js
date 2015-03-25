define([], function() {
  return {
    tweenCardTo: function(card, x, y) {
      if(x != card.x || y != card.y) {
        var tween = card.game.add.tween(card);
        tween.to({ x: x, y: y }, 200);
        tween.onComplete.add(function() {
          card.tweening = false;
        }, card);
        card.tweening = true;
        tween.start();
      }
    },

    tweenRemove: function(card) {
      var tween = card.game.add.tween(card);
      tween.to({ alpha: 0 }, 400);
      tween.onComplete.add(function() {
        card.destroy();
      }, card);
      card.tweening = true;
      tween.start();
    },

    tweenFlip: function(card) {
      var origWidth = card.width;
      var hideTween = card.game.add.tween(card);
      hideTween.to({ width: 0 }, 100);
      hideTween.onComplete.add(function() {
        card.updateSprite(true);
        var showTween = card.game.add.tween(card);
        showTween.to({ width: origWidth }, 100);
        showTween.onComplete.add(function() {
          card.tweening = false;
          console.log(card);
        }, card);
        showTween.start();
      }, card);
      card.tweening = true;
      hideTween.start();
    }
  };
});
