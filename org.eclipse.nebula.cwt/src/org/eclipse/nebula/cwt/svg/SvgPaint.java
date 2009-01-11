package org.eclipse.nebula.cwt.svg;

import org.eclipse.swt.graphics.GC;

public abstract class SvgPaint {

	public enum PaintType { None, Current, Color, Link }
	
	SvgGraphic parent;
	GC gc;
	SvgGradient paintServer;
	PaintType type = null;
	String linkId = null;
	Integer color = null;
	Float opacity = null;

	SvgPaint(SvgGraphic parent) {
		this.parent = parent;
	}
	
	abstract void apply();
	
	public void create(GC gc) {
		if(parent instanceof SvgShape) {
			this.gc = gc;
			if(linkId != null) {
				SvgElement def = parent.getElement(linkId);
				if(def instanceof SvgGradient) {
					SvgGradient gradient = (SvgGradient) def;
					SvgShape shape = (SvgShape) parent;
					paintServer = gradient;
					paintServer.create(shape, gc);
				}
			}
		} else {
			throw new UnsupportedOperationException("only shapes can be painted...");
		}
	}
	
	public boolean dispose() {
		if(paintServer != null) {
			paintServer.dispose();
			return true;
		}
		return false;
	}

	public boolean isPaintable() {
		return type != PaintType.None;
	}
	
}
