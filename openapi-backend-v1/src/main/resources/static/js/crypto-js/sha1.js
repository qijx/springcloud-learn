(function(t,r){"object"==typeof exports?module.exports=exports=r(require("./core")):"function"==typeof define&&define.amd?define(["./core"],r):r(t.CryptoJS)})(this,function(t){return function(){var r=t,n=r.lib,e=n.WordArray,i=n.Hasher,o=r.algo,s=[],a=o.SHA1=i.extend({_doReset:function(){this._hash=new e.init([1732584193,4023233417,2562383102,271733878,3285377520])},_doProcessBlock:function(t,r){for(var n=this._hash.words,e=n[0],i=n[1],o=n[2],a=n[3],f=n[4],c=0;80>c;c++){if(16>c)s[c]=0|t[r+c];else{var u=s[c-3]^s[c-8]^s[c-14]^s[c-16];s[c]=u<<1|u>>>31}var h=(e<<5|e>>>27)+f+s[c];h+=20>c?(i&o|~i&a)+1518500249:40>c?(i^o^a)+1859775393:60>c?(i&o|i&a|o&a)-1894007588:(i^o^a)-899497514,f=a,a=o,o=i<<30|i>>>2,i=e,e=h}n[0]=0|n[0]+e,n[1]=0|n[1]+i,n[2]=0|n[2]+o,n[3]=0|n[3]+a,n[4]=0|n[4]+f},_doFinalize:function(){var t=this._data,r=t.words,n=8*this._nDataBytes,e=8*t.sigBytes;return r[e>>>5]|=128<<24-e%32,r[(e+64>>>9<<4)+14]=Math.floor(n/4294967296),r[(e+64>>>9<<4)+15]=n,t.sigBytes=4*r.length,this._process(),this._hash},clone:function(){var t=i.clone.call(this);return t._hash=this._hash.clone(),t}});r.SHA1=i._createHelper(a),r.HmacSHA1=i._createHmacHelper(a)}(),t.SHA1});