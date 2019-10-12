(function(e,t){"object"==typeof exports?module.exports=exports=t(require("./core")):"function"==typeof define&&define.amd?define(["./core"],t):t(e.CryptoJS)})(this,function(e){e.lib.Cipher||function(t){var r=e,n=r.lib,i=n.Base,o=n.WordArray,s=n.BufferedBlockAlgorithm,a=r.enc;a.Utf8;var c=a.Base64,f=r.algo,h=f.EvpKDF,u=n.Cipher=s.extend({cfg:i.extend(),createEncryptor:function(e,t){return this.create(this._ENC_XFORM_MODE,e,t)},createDecryptor:function(e,t){return this.create(this._DEC_XFORM_MODE,e,t)},init:function(e,t,r){this.cfg=this.cfg.extend(r),this._xformMode=e,this._key=t,this.reset()},reset:function(){s.reset.call(this),this._doReset()},process:function(e){return this._append(e),this._process()},finalize:function(e){e&&this._append(e);var t=this._doFinalize();return t},keySize:4,ivSize:4,_ENC_XFORM_MODE:1,_DEC_XFORM_MODE:2,_createHelper:function(){function e(e){return"string"==typeof e?S:x}return function(t){return{encrypt:function(r,n,i){return e(n).encrypt(t,r,n,i)},decrypt:function(r,n,i){return e(n).decrypt(t,r,n,i)}}}}()});n.StreamCipher=u.extend({_doFinalize:function(){var e=this._process(true);return e},blockSize:1});var l=r.mode={},d=n.BlockCipherMode=i.extend({createEncryptor:function(e,t){return this.Encryptor.create(e,t)},createDecryptor:function(e,t){return this.Decryptor.create(e,t)},init:function(e,t){this._cipher=e,this._iv=t}}),p=l.CBC=function(){function e(e,r,n){var i=this._iv;if(i){var o=i;this._iv=t}else var o=this._prevBlock;for(var s=0;n>s;s++)e[r+s]^=o[s]}var r=d.extend();return r.Encryptor=r.extend({processBlock:function(t,r){var n=this._cipher,i=n.blockSize;e.call(this,t,r,i),n.encryptBlock(t,r),this._prevBlock=t.slice(r,r+i)}}),r.Decryptor=r.extend({processBlock:function(t,r){var n=this._cipher,i=n.blockSize,o=t.slice(r,r+i);n.decryptBlock(t,r),e.call(this,t,r,i),this._prevBlock=o}}),r}(),v=r.pad={},y=v.Pkcs7={pad:function(e,t){for(var r=4*t,n=r-e.sigBytes%r,i=n<<24|n<<16|n<<8|n,s=[],a=0;n>a;a+=4)s.push(i);var c=o.create(s,n);e.concat(c)},unpad:function(e){var t=255&e.words[e.sigBytes-1>>>2];e.sigBytes-=t}};n.BlockCipher=u.extend({cfg:u.cfg.extend({mode:p,padding:y}),reset:function(){u.reset.call(this);var e=this.cfg,t=e.iv,r=e.mode;if(this._xformMode==this._ENC_XFORM_MODE)var n=r.createEncryptor;else{var n=r.createDecryptor;this._minBufferSize=1}this._mode=n.call(r,this,t&&t.words)},_doProcessBlock:function(e,t){this._mode.processBlock(e,t)},_doFinalize:function(){var e=this.cfg.padding;if(this._xformMode==this._ENC_XFORM_MODE){e.pad(this._data,this.blockSize);var t=this._process(true)}else{var t=this._process(true);e.unpad(t)}return t},blockSize:4});var g=n.CipherParams=i.extend({init:function(e){this.mixIn(e)},toString:function(e){return(e||this.formatter).stringify(this)}}),_=r.format={},w=_.OpenSSL={stringify:function(e){var t=e.ciphertext,r=e.salt;if(r)var n=o.create([1398893684,1701076831]).concat(r).concat(t);else var n=t;return n.toString(c)},parse:function(e){var t=c.parse(e),r=t.words;if(1398893684==r[0]&&1701076831==r[1]){var n=o.create(r.slice(2,4));r.splice(0,4),t.sigBytes-=16}return g.create({ciphertext:t,salt:n})}},x=n.SerializableCipher=i.extend({cfg:i.extend({format:w}),encrypt:function(e,t,r,n){n=this.cfg.extend(n);var i=e.createEncryptor(r,n),o=i.finalize(t),s=i.cfg;return g.create({ciphertext:o,key:r,iv:s.iv,algorithm:e,mode:s.mode,padding:s.padding,blockSize:e.blockSize,formatter:n.format})},decrypt:function(e,t,r,n){n=this.cfg.extend(n),t=this._parse(t,n.format);var i=e.createDecryptor(r,n).finalize(t.ciphertext);return i},_parse:function(e,t){return"string"==typeof e?t.parse(e,this):e}}),m=r.kdf={},B=m.OpenSSL={execute:function(e,t,r,n){n||(n=o.random(8));var i=h.create({keySize:t+r}).compute(e,n),s=o.create(i.words.slice(t),4*r);return i.sigBytes=4*t,g.create({key:i,iv:s,salt:n})}},S=n.PasswordBasedCipher=x.extend({cfg:x.cfg.extend({kdf:B}),encrypt:function(e,t,r,n){n=this.cfg.extend(n);var i=n.kdf.execute(r,e.keySize,e.ivSize);n.iv=i.iv;var o=x.encrypt.call(this,e,t,i.key,n);return o.mixIn(i),o},decrypt:function(e,t,r,n){n=this.cfg.extend(n),t=this._parse(t,n.format);var i=n.kdf.execute(r,e.keySize,e.ivSize,t.salt);n.iv=i.iv;var o=x.decrypt.call(this,e,t,i.key,n);return o}})}()});