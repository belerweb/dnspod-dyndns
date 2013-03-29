## DNSPod DynDNS ##

**DNSPod DynDNS** goal is to develop a [dynamic DNS](http://en.wikipedia.org/wiki/Dynamic_DNS) server which contains various implements, e.g. [dyn.com](http://dyn.com/), [GnuDIP](http://gnudip2.sourceforge.net/) ..., supports routers as clients.

### Supported Router ###
 - Router with dyndns(members.dyndns.org) supported.
 - Router with qdns(members.3322.org) supported.

### Join Me ###
DNSPod DynDNS is under development, I expect your support, contribution and etc.

### Best Practice ###
HUAWEI HG8245 + DNSPod DynDNS + DNSPod + Heroku

I have a HUAWEI HG8245 support dyndns.org as dynamic dns provider. But dyndns.org is not free now. I deploy DNSPod DynDNS to heroku, with DNSPod's support. Then I have a server like dyndns.org. HG8245 sends dynamic ip to my server, server calls DNSPod's API.

**All free, it's perfect.**

### Thanks ###
 - **[DNSPod](http://www.dnspod.com/)** DNSPod is the most security and stable free DNS hosting services provider.
 - **[DNSPod JAVA](https://github.com/belerweb/dnspod-java)** DNSPod API written in java.
 - **[Heroku](http://www.heroku.com/)** Heroku is the leading open language cloud application platform and supports Ruby, Java, Python, Clojure, Scala, Node.js. and custom language buildpacks.
 - **[dyn.com](http://dyn.com/)** Named dyndns.org previously.

