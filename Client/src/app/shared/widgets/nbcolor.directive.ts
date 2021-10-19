import { Directive, ElementRef, Input } from '@angular/core';

@Directive({
  selector: '[appNbcolor]'
})
export class NbcolorDirective {

  @Input()
  isNV: boolean;
  @Input()
  numberV: number;

  constructor(private elementRef: ElementRef) {
  }

  ngOnChanges() {

    if (!this.isNV) {
      this.handleColor()
    }
  }

  handleColor() {
    let value: number = this.numberV;
    let style = this.elementRef.nativeElement.style;
    if (value > 0) {
      style.color = '#2e86de'
    } else if (value < 0) {
      style.color = '#ee5253'
    } else {
      style.color = '#8395a7'
    }
  }

}
