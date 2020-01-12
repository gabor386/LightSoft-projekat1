import { Component, OnInit } from '@angular/core';
import { FixtureService } from '../services/fixtures.service';

import { ActivatedRoute } from '@angular/router';
import 'rxjs/add/operator/filter';

@Component({
  selector: 'app-fixtures',
  templateUrl: './fixtures.component.html',
  styleUrls: ['./fixtures.component.css']
})
export class FixturesComponent implements OnInit {

  fixtures: any [];
  idLeage: any;
  constructor(private service: FixtureService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.queryParams
    .filter(params => params.leagues)
    .subscribe(params => {
      this.idLeage = params.leagues;
      this.service.getById(this.idLeage)
      .subscribe( fixtures => { this.fixtures = fixtures;
      });

    });
    
  }

}

