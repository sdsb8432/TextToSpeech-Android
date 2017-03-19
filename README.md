# Text To Speech with Google for Android

구글이 제공하는 TextToSpeech Class를 활용해 텍스트를 음성으로 변환하는 프로젝트이다.


## 1. 사용법

### 1. 클래스 선언

```
private TextToSpeech textToSpeech;

@Override
protected void onCreate(Bundle savedInstanceState) {
	textToSpeech = new TextToSpeech(this, this);
}
```

### 2. interface, abstract 클래스 선언

```
//음성 재생 상태에 대한 callback을 받을 수 있는 추상 클래스
private UtteranceProgressListener progressListener = new UtteranceProgressListener() {
	@Override
	public void onStart(String utteranceId) { // 음성이 재생되었을 때
            
	}

	@Override
	public void onDone(String utteranceId) { // 제공된 텍스트를 모두 음성으로 재생한 경우

	}

	@Override
	public void onError(String utteranceId) { // ERROR!

	}
};

textToSpeech.setOnUtteranceProgressListener(progressListener);
    
```

아래 `setLanguage()` 메소드는 음성 언어를 설정할 수 있다. Locale.ENGLISH, Locale.CANADA 등 상황에 맞춰 필요한 음성을 선택해 사용하면 된다.


```
//음성 관련 초기화 상태에 대한 callback을 받을 수 있는 인터페이스
private TextToSpeech.OnInitListener initListener = new TextToSpeech.OnInitListener() {
	@Override
	public void onInit(int status) {
		if(status != TextToSpeech.ERROR)
			textToSpeech.setLanguage(Locale.KOREAN); // 한글로 설정
	}
};
```

### 3. start & stop & shutdown

####1. start


설명하기 앞서 'utteranceID'는 단어 그대로 현재 재생중인 음성에 대한 ID값을 나타낸다.
여러 개의 음성을 컨트롤할 때 유용하게 사용될 것 같다.

기존의 `speak(String text, int queueMode, HashMap<String, String> params)` 메소드는 API Level 21부터 deprecated 되었다. 그러니 API level에 맞춰 21 이상은 아래와 같이 가급적 새로운  `speak(CharSequence text, int queueMode, Bundle params, String utteranceId)`메소드를 사용하도록 하자.

```
String text = editText.getText.toString();

if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
	String myUtteranceID = "myUtteranceID";
	textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, myUtteranceID);
	}
else {
	HashMap<String, String> hashMap = new HashMap<>();
	hashMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "myUtteranceID");
	textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, hashMap);
	}
```

####2. stop


현재 재생중인 음성에 대한 정지는 간단하게 `textToSpeech.stop()`와 같은 메소드를 사용하면 된다.

####3. shutdown

Speech Engine을 통해 초기화된 TextToSpeech 인스턴트는 꼭 `textToSpeech.shutDown()`을 통해 완전히 종료시켜 주어야 한다. 아니면 SeviceConnection...어쩌구저쩌구하는 Exception이 발생된다.

ex. 아래와 같이 Activity일 경우 생명주기에 맞춰 `shutDown()`을 호출 할 수 있다.

```
@Override
protected void onDestroy() {
	if(textToSpeech != null)
		textToSpeech.shutDown();
	super.onDestroy();
}
```

##2. 프로젝트

해당 프로젝트는 TTS Class를 생성하여 조금 더 사용하기 편하도록 만들어두었다.
이 후 목소리와 재생 속도에 관련된 부분을 프로젝트에 추가할 예정이다.


