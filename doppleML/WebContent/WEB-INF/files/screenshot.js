var page = require('webpage').create();
  system = require('system');
 var address = system.args[1];
 var output = system.args[2];
page.viewportSize = {width:1024, height:800};
page.settings.resourceTimeout = 90000; // 5 seconds
page.onResourceTimeout = function(e) {
  console.log(e.errorCode);   // it'll probably be 408 
  console.log(e.errorString); // it'll probably be 'Network timeout on resource'
  console.log(e.url);         // the url whose request timed out
  phantom.exit(1);
};

var d = new Date();
page.open(address, function(status) {
    if (status !== 'success') {
        console.log('Unable to load the address!');
        phantom.exit();
    } else {
        window.setTimeout(function () {
            page.render(output);
            phantom.exit();
        }, 90000); // Change timeout as required to allow sufficient time 
    }
    //90000
 // page.render('testing'+d+'.pdf');
//  page.render('testing'+d+'.png');
//  phantom.exit();
});

