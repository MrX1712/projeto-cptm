const track = document.querySelector('.notification-track');
const slides = document.querySelectorAll('.notification-item');

let currentIndex = 0;
let isUserScrolling = false;
let autoScrollTimeout;

function scrollToIndex(index) {
    if (index < 0) index = slides.length - 1;
    if (index >= slides.length) index = 0;
    currentIndex = index;
    const slideHeight = slides[0].offsetHeight + 15;
    track.scrollTo({ top: slideHeight * index, behavior: 'smooth' });
}

function autoScroll() {
    if (isUserScrolling) return;
    scrollToIndex(currentIndex + 1);
    autoScrollTimeout = setTimeout(autoScroll, 5000);
}

track.addEventListener('scroll', () => {
    isUserScrolling = true;
    clearTimeout(autoScrollTimeout);

    clearTimeout(track.userScrollTimeout);
    track.userScrollTimeout = setTimeout(() => {
        isUserScrolling = false;
        autoScroll();
    }, 2000);

    const scrollTop = track.scrollTop;
    const slideHeight = slides[0].offsetHeight + 15;
    currentIndex = Math.round(scrollTop / slideHeight);
});

autoScroll();
