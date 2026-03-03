import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FilterComponent } from '../filter/filter.component';

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['./explore.component.scss'],
  imports: [FormsModule, FilterComponent]
})
export class ExploreComponent  implements OnInit {

  searchString: string = '';
  isFilterOpen: boolean = false;

  constructor() { }

  ngOnInit() {}

  onSearch() {
    console.log('Search for:', this.searchString);
  }

}
