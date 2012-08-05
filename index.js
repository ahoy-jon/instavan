// Register the jquery plugin that will create the postcard
jQuery.fn.instavanPostcard = function(params) {
  // Flags indicating if resources are ready
  this.spriteIsReady = false;
  this.frontIsReady = false;
  this.backIsReady = true;
  // Set default params
  params = jQuery.extend( {
    message: "",
    font: "angelina",
    sprite: "img/sprite-default.png", 
    photo: "img/photo-default.png",
    filter: "Bourriquet"}, params);
  // Launch the rendering on each node
  this.each(function() {
    launchRenderingOnNode($(this), params);
  });
  // fluent me
  return this;
};

/**
 * Function responsible for creating the postcard out of the photo, 
 * sprite and message.
 */
function launchRenderingOnNode (jqNode, params) {
  // Not everything is ready
  //if ( (! spriteLoaded) || (! photoLoaded) ) return;
  
  // Prepare the canvas
  console.log("Prepare the canvas...");
  if (jqNode[0].getContext == undefined) {
    console.log("Unable to access canvas context !");
    return;
  }

  mylog("PreRendering"); 
  var context = jqNode[0].getContext("2d");
  jqNode.attr("width", "500");
  jqNode.attr("height", "500");
  context.globalCompositeOperation = 'source-over';
  context.rect(1, 1, jqNode.attr("width")-2, jqNode.attr("height")-2);
  context.stroke();
  context.rect(9, 9, 482, 402);
  context.stroke();
  
  // Render the photo
  console.log("Render the photo...");

  mylog("PreLoad"); 
  var photo = new Image();
  photo.src = params["photo"];
  mylog("PostPath"); 
  photo.onload = function() { 
    mylog("PreImage"); 
    var camanImg = Caman(photo);
     // Apply filter
 
   applyVANFilter(camanImg);
 

  camanImg.render(function() {
    mylog("Rendering ok!");
    drawRenderedImage(context, this, params);
  });
  
  };
}

/** Filters */
function applyVANFilter(camanImg) {
  camanImg.brightness(-5); // dark side
  camanImg.vibrance(-20); // unsaturate
  camanImg.contrast(20); // raise contrast
}

/**
 * Function responsible for doing the rendering.
 */
function drawRenderedImage(context, camanImg, params) {
  // Draw the rendering 
  mylog("Draw rendering...");
  context.drawImage(camanImg.canvas, 10, 10, 480, 400);
  
  // Handle compositing
  mylog("Compositing sprite...");
  var x = 10 + 480 - 145;
  var y = 10 + 400 - 120;
  var sprite = new Image();
  sprite.src = params["sprite"];
  context.drawImage(sprite, x, y, 150, 150);
}

function sticker() {
  var context = $("#postcard").getContext("2d")

  var x = 10 + 480 - 145;
  var y = 10 + 400 - 120;
  var sprite = new Image();
  sprite.src =  "sprite-VAN.png";
  context.drawImage(sprite, x, y, 150, 150);

}


var mylog = function(s) {

  $("#log").html($("#log").html() + s + "<br/>")
}






var app = {
    initialize: function() {
        this.bind();
    },
    bind: function() {
        document.addEventListener('deviceready', this.deviceready, false);
    },
    deviceready: function() {
        // note that this is an event handler so the scope is that of the event
        // so we need to call app.report(), and not this.report()
        app.report('deviceready');
    },
    report: function(id) { 
        console.log("report:" + id);
        // hide the .pending <p> and show the .complete <p>
        document.querySelector('#' + id + ' .pending').className += ' hide';
        var completeElem = document.querySelector('#' + id + ' .complete');
        completeElem.className = completeElem.className.split('hide').join('');
    }
};



var camera = function() {
    var pictureSuccess = function(imageData) { 
      var path = imageData; //"data:image/jpeg;base64," +  imageData;//"data:image/jpeg;base64," + imageData;
       //path = "prite-VAN.png";
    /*mylog(path);

    var html = '<img height="300" width="300" src=' + path + '/>';
          //  mylog(html);
            $("#pics").append(html);*/

       $("#postcard").instavanPostcard({
          "message": "DAFUCK",
          "sprite": "sprite-VAN.png",
          "photo": path
        });

    } 


    // capture error callback
    var captureError = function(error) {
        console.log("error");
        navigator.notification.alert('Error code: ' + error.code, null, 'Capture Error');
    };

    
    navigator.camera.getPicture(pictureSuccess, captureError, 
      {destinationType:  Camera.DestinationType.FILE_URI ,
encodingType: Camera.EncodingType.JPEG,
 targetHeight: 500
})
    // start image capture
    //navigator.device.capture.captureImage(captureSuccess, captureError, {limit:1});
}
