import { Component, OnInit } from '@angular/core';
import { LeaguesService } from '../services/leagues.service';

@Component({
  selector: 'app-bs-navbar',
  templateUrl: './bs-navbar.component.html',
  styleUrls: ['./bs-navbar.component.css']
})
export class BsNavbarComponent implements OnInit {
  leagues: any [];

  constructor(private service: LeaguesService) { }

  ngOnInit() {
    this.service.getAll()
    .subscribe(response => {
       this.leagues = response.json();
    }, error => {
      alert('An enexpected error occurrend');
    });
  }

}
