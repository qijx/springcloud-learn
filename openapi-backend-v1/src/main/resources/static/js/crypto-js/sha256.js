(function(t,r){"object"==typeof exports?module.exports=exports=r(require("./core")):"function"==typeof define&&define.amd?define(["./core"],r):r(t.CryptoJS)})(this,function(t){return function(r){var e=t,n=e.lib,i=n.WordArray,o=n.Hasher,s=e.algo,a=[],f=[];(function(){function t(t){for(var e=r.sqrt(t),n=2;e>=n;n++)if(!(t%n))return!1;return!0}function e(t){return 0|4294967296*(t-(0|t))}for(var n=2,i=0;64>i;)t(n)&&(8>i&&(a[i]=e(r.pow(n,.5))),f[i]=e(r.pow(n,1/3)),i++),n++})();var c=[],u=s.SHA256=o.extend({_doReset:function(){this._hash=new i.init(a.slice(0))},_doProcessBlock:function(t,r){for(var e=this._hash.words,n=e[0],i=e[1],o=e[2],s=e[3],a=e[4],u=e[5],h=e[6],d=e[7],p=0;64>p;p++){if(16>p)c[p]=0|t[r+p];else{var l=c[p-15],y=(l<<25|l>>>7)^(l<<14|l>>>18)^l>>>3,v=c[p-2],g=(v<<15|v>>>17)^(v<<13|v>>>19)^v>>>10;c[p]=y+c[p-7]+g+c[p-16]}var _=a&u^~a&h,m=n&i^n&o^i&o,x=(n<<30|n>>>2)^(n<<19|n>>>13)^(n<<10|n>>>22),w=(a<<26|a>>>6)^(a<<21|a>>>11)^(a<<7|a>>>25),B=d+w+_+f[p]+c[p],A=x+m;d=h,h=u,u=a,a=0|s+B,s=o,o=i,i=n,n=0|B+A}e[0]=0|e[0]+n,e[1]=0|e[1]+i,e[2]=0|e[2]+o,e[3]=0|e[3]+s,e[4]=0|e[4]+a,e[5]=0|e[5]+u,e[6]=0|e[6]+h,e[7]=0|e[7]+d},_doFinalize:function(){var t=this._data,e=t.words,n=8*this._nDataBytes,i=8*t.sigBytes;return e[i>>>5]|=128<<24-i%32,e[(i+64>>>9<<4)+14]=r.floor(n/4294967296),e[(i+64>>>9<<4)+15]=n,t.sigBytes=4*e.length,this._process(),this._hash},clone:function(){var t=o.clone.call(this);return t._hash=this._hash.clone(),t}});e.SHA256=o._createHelper(u),e.HmacSHA256=o._createHmacHelper(u)}(Math),t.SHA256});