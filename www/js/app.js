console.log("Ciel App Loaded");

// Fungsi Refresh
function refreshBrowser() {
    window.location.reload();
}

// Fungsi TTS (Native)
function speak(text) {
    if (window.BottoNativeTTS) {
        window.BottoNativeTTS.speak(text);
    }
}

function stopSpeak() {
    if (window.BottoNativeTTS) {
        window.BottoNativeTTS.stop();
    }
}
