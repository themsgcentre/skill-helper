import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IonRange } from "@ionic/angular/standalone";

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss'],
  imports: [IonRange, FormsModule],
})
export class FilterComponent  implements OnInit {

  constructor() { }

  @Output() rangeChange = new EventEmitter<{lower: number, upper: number}>();

  stressRange = {
    lower: 0,
    upper: 100
  }

  ngOnInit() {}

  adjustRange({lower, upper}: {lower: number, upper: number}) {
    this.stressRange = { lower, upper };
    console.log(this.stressRange);
  }
}
