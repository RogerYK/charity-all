import React, {Component} from 'react'
import './style.scss'
import Header from '../../components/Header'
import Footer from '../../components/Footer'
import ProjectShow from './ProjectShow';
import Advantage from './Advantage';
import AboutUs from './AboutUs';

export class HomePage extends Component {

    render() {
        return (
            <div className="about-page">
                <Header />
                <div className="container">
                    <AboutUs />
                    <Advantage />
                    <ProjectShow />
                </div>
                <Footer />
            </div>
        )
    }
}