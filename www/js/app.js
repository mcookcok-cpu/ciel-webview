console.log("Ciel App Loaded");

// Fungsi Refresh
function refreshBrowser() {
    window.location.reload();
}

// Fungsi TTS (Native)
function speak(text) {
    if (window.BottoNativeTTS) {
        window.BottoNativeTTS.speak(text);
    } else {
        alert("Native TTS not available");
    }
}
