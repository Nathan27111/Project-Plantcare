const CACHE_NAME = "plantcare";

const CACHED_URLS = [
    "/",
    "/index.html",
    "/assets/css/main.css",
    "/assets/css/index.css",
    "/home.html",
    "/assets/css/home.css",
    "/assets/js/home.js"
]

self.addEventListener("install", function (e){
    e.waitUntil(
        caches.open(CACHE_NAME).then(cache => {
            return cache.addAll(CACHED_URLS);
        })
    )
})

self.addEventListener("fetch", function(e) {
    e.respondWith(fetch(e.request)
        .catch(e => {
            return caches.open(CACHE_NAME).then(cache => {
                return cache.match(e.request);
            })
        }));
});
