(function(e,t){"object"==typeof exports?module.exports=exports=t(require("./core"),require("./cipher-core")):"function"==typeof define&&define.amd?define(["./core","./cipher-core"],t):t(e.CryptoJS)})(this,function(e){return e.pad.Iso10126={pad:function(t,r){var i=4*r,n=i-t.sigBytes%i;t.concat(e.lib.WordArray.random(n-1)).concat(e.lib.WordArray.create([n<<24],1))},unpad:function(e){var t=255&e.words[e.sigBytes-1>>>2];e.sigBytes-=t}},e.pad.Iso10126});