module Team81 {
	exports game.engine.dataloader;
	exports game.engine.weapons;
	exports game.engine.lanes;
	exports game.tests;
	exports game.engine.exceptions;
	exports game.engine.interfaces;
	exports game.engine.weapons.factory;
	exports game.engine;
	exports game.engine.base;
	exports game.engine.titans;
	exports game.gui;

	requires junit;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
	requires java.base;
	requires javafx.fxml;
	requires javafx.media;
	opens game.gui to javafx.fxml;
}