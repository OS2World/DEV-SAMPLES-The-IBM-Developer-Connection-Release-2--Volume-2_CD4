TickerTape.jar : sdm/util/TickerTape.class sdm/util/TickerTapeBeanInfo.class sdm/util/TickerTapeIcon.gif
	jar -cfm TickerTape.jar TickerTape.mf   sdm/util/*.class  sdm/util/*.gif

sdm/util/TickerTape.class : TickerTape.java
	javac -d . TickerTape.java

sdm/util/TickerTapeBeanInfo.class : TickerTapeBeanInfo.java
	javac -d . TickerTapeBeanInfo.java
