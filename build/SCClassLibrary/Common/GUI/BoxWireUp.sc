// the beginnings of a graphical box wire-up language.

/*

BoxPort { // implements individual inlets and outlets
	var <>wires, <>value;
	*new {
		^super.new.wires_(Array.new(1))
	}
	
	send { arg selector, args;
		wires.do({ arg wire; 
			wire.send(selector, args);
		});
	}
	disconnect {
		wires.copy.do({ arg wire; wire.disconnect });
	}
	addWire { arg wire;
		wires = wires.add(wire);
	}
}

BoxWire { // implements wires between ports
	var <>outBox, <>outletIndex, <>inBox, <>inletIndex;
	*new { arg outBox, outletIndex, inBox, inletIndex;
		^super.newCopyArgs(outBox, outletIndex, inBox, inletIndex)
	}
	send { arg selector, args;
		inBox.recv(inletIndex, selector, args);
	}
	disconnect {
		outBox.outlets.at(outletIndex).wires.take(this);
		inBox.inlets.at(inletIndex).wires.take(this);
	}
}

Box { // implements components
	var <>bounds, <>patch, <>inlets, <>outlets, curInlet, properties;
	
	*new { arg bounds, patch;
		^super.new.initBox(bounds, patch)
	}
	
	*connect { arg outBox, outletIndex, inBox, inletIndex;
		outBox.outlets.put(outletIndex, BoxPort(inletIndex, inBox));
		inBox.inlets.put(inletIndex, BoxPort(outletIndex, outBox));
	}
	delete {
		this.disconnect;
		patch.remove(this);
	}
	
	initBox { arg argBounds, argPatch;
		inlets  = Array.new(1);
		outlets = Array.new(1);
		bounds = argBounds;
		argPatch.add(this);
	}

	addInlet {
		inlets = inlets.add(BoxPort.new);
	}
	addOutlet {
		outlets = outlets.add(BoxPort.new);
	}
	
	disconnectOutlet { arg index;
		outlets.at(index).disconnect;
	}
	disconnectInlet { arg index;
		inlets.at(index).disconnect;
	}
	disconnect {
		inlets.do({ arg port; port.disconnect });
		outlets.do({ arg port; port.disconnect });
	}
	
	send { arg outletIndex, selector ... args;
		outlets.at(outletIndex).send(selector, args);
	}
	
	recv { arg inletIndex, selector, args;
		var prevInlet;
		prevInlet = curInlet;
		curInlet = inletIndex;
		this.performList(selector, args);
		curInlet = prevInlet;
	}
	set { arg val;
		inlets.at(curInlet).value = val;
	}
}


PatchBox : Box { // PatchBox holds other boxes in a subpatch
	var <>boxes, <>inboxes, outboxes;
	
	initPatchBox {
		inboxes  = Array.new(1);
		outboxes = Array.new(1);
	}
	add { arg box;
		box.patch = this;
		boxes = boxes.add(box);
	}
	remove { arg box;
		boxes.take(box);
	}
	
	recv { arg inletIndex, selector, args;
		var prevInlet;
		prevInlet = curInlet;
		curInlet = inletIndex;
		inboxes.at(inletIndex).send(0, selector, args);
		curInlet = prevInlet;
	}
}


InBox : Box { // represents an inlet to the subpatch
	var <>index;
	patch_ { arg box;
		patch = box;
		index = box.inboxes.size;
		box.inboxes = box.inboxes.add(this);
	}
	recv { arg inletIndex, selector, args;
		this.send(0, selector, args);
	}
}


OutBox : Box { // represents an outlet to the subpatch
	var <>index;
	patch_ { arg box;
		patch = box;
		index = box.outboxes.size;
		box.outboxes = box.outboxes.add(this);
	}
	recv { arg inletIndex, selector, args;
		patch.outboxes.at(index).send(0, selector, args);
	}
}


PatchBoxView { // view of a patch.
	var <>patch;
	
	draw {
		this.drawAllBoxes;
		this.drawAllWires;
	}
	
	drawAllBoxes {
		patch.boxes.do({ arg box;
			this.drawBox(box);
		});
	}
	drawAllWires {
		var lines;
		lines = Lines.new;
		patch.boxes.do({ arg box;
			box.outlets.do({ arg port;
				port.wires.do({ arg wire;
					this.addWire(wire, lines);
				});
			});
		});
		lines.draw;
	}
	addWire { arg wire, lines;
		var start, end;
		start = this.outletPoint(wire.outletBox, wire.outletIndex);
		end = this.inletPoint(wire.inletBox, wire.inletIndex);
		lines.add(start);
		lines.add(end);
	}
	
	drawBox { arg box;
		Pen(foreColor: Color.white, action: \fill).use({
			box.bounds.draw;
		});
		Pen(foreColor: Color.black, action: \frame).use({
			box.bounds.draw;
		});
		this.drawBoxPorts(box);
	}
	drawBoxPorts { arg box;
		Pen(foreColor: Color.black, action: \fill).use({
			box.inlets.size.do({ arg i;
				this.inletRect(box, i, 2, 1).draw;
			});
			box.outlets.size.do({ arg i;
				this.outletRect(box, i, 2, 1).draw;
			});
		});
	}
	
	inletPoint { arg box, index;
		var spacing;
		spacing = box.bounds.width / box.inlets.size;
		^Point( spacing * (index + 0.5), patch.bounds.top );
	}
	outletPoint { arg box, index;
		var spacing;
		spacing = box.bounds.width / box.outlets.size;
		^Point( spacing * (index + 0.5), patch.bounds.bottom );
	}
	
	inletRect { arg box, index, size = 2;
		^Rect.aboutPoint(this.inletPoint(box, index), size, size)
	}
	outletRect{ arg box, index, size = 2;
		^Rect.aboutPoint(this.outletPoint(box, index), size, size)
	}
}
*/
