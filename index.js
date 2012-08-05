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


  
  /*
  // trick
  context.onerror = function() {
    this.textAlign = "center";
    this.font = '30px "' + params["font"] + '"';
    this.fillText(params["message"], 250, 450);
  };
  
  // Draw the text
  console.log("Draw the text...");
  context.textAlign = "center";
  context.font = '30px "' + params["font"] + '"';
  //context.font = '30px "Arial"';
  var message = "";
  if (params["message"].length > 50) {
    console.log("Text longer than expected: split on two lines");
    var strarr = params["message"].split(" ");
    for(var i=0 ; i<strarr.length ; i++) {
      message += " " + strarr[i];
      if (i == Math.floor(strarr.length/2)) {
        context.fillText(message, 250, 455, jqNode.attr("width")-20);
        message = "";
      }
    }
    context.fillText(message, 250, 485, jqNode.attr("width")-20);
  } else {
    message = params["message"];
    context.fillText(message, 250, 455, jqNode.attr("width")-20);
  }*/
}

/** Filters */
function applyVANFilter(camanImg) {
  camanImg.brightness(-5); // dark side
  camanImg.vibrance(-20); // unsaturate
  camanImg.contrast(20); // raise contrast
}
function applyBourriquetFilter(camanImg) {
  camanImg.colorize("#a6b6c8", 50); // make it blue baby
  camanImg.brightness(-5); // dark side
  camanImg.vibrance(-20); // unsaturate
  camanImg.contrast(20); // raise contrast
}
function applyPorcinetFilter(camanImg) {
  camanImg.colorize("#f279ac", 50); // make it pink baby
  camanImg.brightness(-5); // dark side
  camanImg.vibrance(-20); // unsaturate
  camanImg.contrast(20); // raise contrast
}
function applyTigrouFilter(camanImg) {
  camanImg.colorize("#c3a99a", 70); // make it orange baby
  camanImg.brightness(-5); // dark side
  camanImg.contrast(40); // raise contrast
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

/*
// Sprites
console.log("Chargement des sprites...");
var sprite = new Image();
sprite.onload = function () {
  console.log("Sprite image loaded...");
  spriteLoaded = true;
  launchRendering();
};
//sprite.src = "img/sprite-spiderman.png";
sprite.src = params["sprite"];

// Photos
console.log("Chargement des photos...");
var photo = new Image();
photo.onload = function () {
  console.log("Photo image loaded...");
  photoLoaded = true;
  launchRendering();
};
photo.src = "crepetown-1.jpg";

function launchCompositing(camanImg) {
  var x = camanImg.canvas.width - 160; //x = 10;
  var y = camanImg.canvas.height - 160; // y = 10;
  console.log("Compositing du sprite en ("+x+", "+y+")...");
  var context = camanImg.canvas.getContext("2d");
  context.globalCompositeOperation = 'source-over';
  context.drawImage(sprite, x, y, 150, 150);
  console.log("done !");
}
*/


var mylog = function(s) {

  $("#log").html($("#log").html() +s + "<br/>")
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

var album = function() {

    var success = function(imageData) {
        console.log("success");
      /*$.ajax(App.Config.api_url + "users/" + App.currentUser.get("id"), {
        type: "POST",
        data: {
          token: App.currentUser.get("token"),
          secret: App.currentUser.get("secret"),
          avatar: imageData
        },
        success: function(response) {
          App.currentUser.set("avatar", response.filename);
          localStorage.setItem("currentUser", JSON.stringify(App.currentUser));
          $(that.el).find(".profile-edit-avatar").attr("src", App.Config.avatarsPath + response.filename);
          $("#loading-container-custom").hide();
        },
        error: function(error) {
          console.log(error);
          $("#loading-container-custom").hide();
          triggerAlert("Erreur lors de l'upload de l'avatar.");

        }
      });*/
    };

    var fail = function(message) {
      console.log(message);
      //$("#loading-container-custom").hide();
    };

    navigator.camera.getPicture(success, fail, {
      destinationType: navigator.camera.DestinationType.DATA_URL,
      sourceType: navigator.camera.PictureSourceType.PHOTOLIBRARY,
      quality: 49,
      targetWidth: 500,
      targetHeight: 500
    });
}

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

    // capture callback
    /*var captureSuccess = function(mediaFiles) {
        console.log("success");
        var i, path, len;
        for (i = 0, len = mediaFiles.length; i < len; i += 1) {
            path = mediaFiles[i].fullPath;
            console.log(path);



            mylog(path);

        $("#postcard").instavanPostcard({
          "message": "DAFUCK",
          "sprite": "sprite-VAN.png",
          "photo": "sprite-VAN.png"
        });
            // do something interesting with the file
        }
    };*/

    // capture error callback
    var captureError = function(error) {
        console.log("error");
        navigator.notification.alert('Error code: ' + error.code, null, 'Capture Error');
    };

    
    navigator.camera.getPicture(pictureSuccess, captureError, 
      {quality:100, destinationType:  Camera.DestinationType.FILE_URI ,
encodingType: Camera.EncodingType.JPEG,
        targetWidth: 100,
  targetHeight: 100
})
    // start image capture
    //navigator.device.capture.captureImage(captureSuccess, captureError, {limit:1});
}
