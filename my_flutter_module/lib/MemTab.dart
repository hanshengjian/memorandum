import 'package:flutter/material.dart';

const double _kTabHeight = 46.0;
const double _kTextAndIconTabHeight = 72.0;

class MemTab extends Tab {
  MemTab(String title) : super(text: title);

  Widget _buildLabelText() {
    return child ??
        Column(
          children: [
            Stack(
              alignment: Alignment.center,
              children: [
                Positioned(
                  left: 10.0,
                  child: Text(
                    "98",
                    style: TextStyle(color: Colors.red),
                  ),
                ),
              ],
            ),
            Text(text!, softWrap: false, overflow: TextOverflow.fade)
          ],
        );
  }

  @override
  Widget build(BuildContext context) {
    assert(debugCheckHasMaterial(context));

    final double height;
    final Widget label;
    if (icon == null) {
      height = _kTabHeight;
      label = _buildLabelText();
    } else if (text == null && child == null) {
      height = _kTabHeight;
      label = icon!;
    } else {
      height = _kTextAndIconTabHeight;
      label = Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Container(
            child: icon,
            margin: iconMargin,
          ),
          _buildLabelText(),
        ],
      );
    }

    return SizedBox(
      height: height,
      width: 400,
      child: Stack(
        alignment: Alignment.center,
        children: [
          Positioned(
            right: 20,
            top: 6,
            child: Text("98"),
          ),
          Text(text!, softWrap: false, overflow: TextOverflow.fade)
        ],
      ),
    );
  }
}
