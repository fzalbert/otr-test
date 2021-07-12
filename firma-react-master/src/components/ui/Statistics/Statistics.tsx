import React, { useEffect, useRef, Ref, RefObject } from 'react';
import './Statistics.scss';
import moment from 'moment';
import arrowTopIcon from '../../../assets/images/icons/arrow-top.svg';
import arrowBottomIcon from '../../../assets/images/icons/arrow-bottom.svg';

const Statistics = (props:{ ratings: any[] }) => {
    props.ratings.sort((a, b) => a.date - b.date);

    // For test
    const salaryArray:any[] = [
        {salary: 3200, date: new Date(2013, 11, 14, 3, 0, 0)},
        {salary: 1000, date: new Date(2011, 4, 30, 3, 0, 0)},
        {salary: 2400, date: new Date(2012, 1, 17, 3, 0, 0)},
        {salary: 1250, date: new Date(2016, 8, 4, 11, 0, 0)},
        {salary: 200, date: new Date(2019, 11, 7, 3, 0, 0)},
        {salary: 2200, date: new Date(2018, 11, 20, 3, 0, 0)},
        {salary: 2100, date: new Date(2006, 11, 25, 3, 0, 0)},
        {salary: 4200, date: new Date(2020, 7, 4, 3, 0, 0)},
        {salary: 3200, date: new Date(2010, 6, 6, 3, 0, 0)},
        {salary: 3500, date: new Date(2018, 9, 9, 3, 0, 0)},
        {salary: 3900, date: new Date(2017, 4, 21, 3, 0, 0)},
        {salary: 5000, date: new Date(2014, 7, 26, 3, 0, 0)}
    ]

    const withdrawalArray:any[] = [
        {withdrawal: 3200, date: new Date(2013, 11, 14, 3, 0, 0)},
        {withdrawal: 1000, date: new Date(2011, 4, 30, 3, 0, 0)},
        {withdrawal: 2400, date: new Date(2012, 1, 17, 3, 0, 0)},
        {withdrawal: 1250, date: new Date(2016, 8, 4, 11, 0, 0)},
        {withdrawal: 200, date: new Date(2019, 11, 7, 3, 0, 0)},
        {withdrawal: 2200, date: new Date(2018, 11, 20, 3, 0, 0)},
        {withdrawal: 2100, date: new Date(2006, 11, 25, 3, 0, 0)},
        {withdrawal: 4200, date: new Date(2020, 7, 4, 3, 0, 0)},
        {withdrawal: 3200, date: new Date(2010, 6, 6, 3, 0, 0)},
        {withdrawal: 3500, date: new Date(2018, 9, 9, 3, 0, 0)},
        {withdrawal: 3900, date: new Date(2017, 4, 21, 3, 0, 0)},
        {withdrawal: 5000, date: new Date(2014, 7, 26, 3, 0, 0)}
    ]
    // for test

    const getMiddle = (arr:any[], prop:string) => (arr.reduce((acum, current) => acum + current[prop], 0) / arr.length).toFixed(2);
    const middleRating = getMiddle(props.ratings, 'rating');
    const middleSalary = getMiddle(salaryArray, 'salary');
    const middleWithdrawal = getMiddle(withdrawalArray, 'withdrawal');

    // refs to canvases
    const ratingsCanvas = useRef<HTMLCanvasElement>(null);
    const salaryCanvas = useRef<HTMLCanvasElement>(null);
    const withdrawalCanvas = useRef<HTMLCanvasElement>(null);

    const drawChart = (arrayX:any, canvasRef:RefObject<HTMLCanvasElement>) => {
        arrayX.sort((a:any,b:any) => a.x - b.x); // sort x coords
        if(canvasRef.current) {
            const canvas:HTMLCanvasElement = canvasRef.current;
            const context = canvas.getContext('2d');
            if(context) {
                // Max and min y coords
                const sortedY = [...arrayX].sort((a,b) => a.y - b.y);
                const minY = sortedY[0].y;
                const maxY = sortedY[sortedY.length - 1].y;

                // unit coords
                const unitCoordX = 246 / (arrayX[arrayX.length - 1].x - arrayX[0].x);
                const unitCoordY = 60 / (maxY - minY);
                // Points
                const coordsX = arrayX.map((item:any) => unitCoordX * (item.x - arrayX[0].x) + 13.5);
                const coordsY = arrayX.map((item:any) => {
                    const value = 60 - unitCoordY * (item.y - minY) + 10;
                    return value < 0 ? -value : value;
                });

                // Styles
                let gradient=context.createLinearGradient(0,60,246,0);
                gradient.addColorStop(0,"#2E68E4");
                gradient.addColorStop(1.0,"#2EEFC1");

                context.strokeStyle = gradient;
                context.lineCap = 'round';
                context.lineJoin = 'round';
                context.lineWidth = 2;

                context.shadowOffsetX = 5;
                context.shadowOffsetY = 5;
                context.shadowBlur = 5;
                context.shadowColor = 'rgba(0, 0, 0, 0.5)';

                // Build chart
                context.beginPath();
                context.moveTo(coordsX[0], coordsY[0]);
                coordsX.forEach((c:any, i:number) => {
                    i !== coordsX.length - 1 ? context.lineTo(coordsX[i+1], coordsY[i+1]) : null;
                    context.moveTo(coordsX[i+1], coordsY[i+1])
                });

                context.closePath();
                context.stroke();
            }
        }
    }

    useEffect(() => {
        drawChart(props.ratings.map(item => ({x: item.date, y: item.rating})), ratingsCanvas);
        drawChart(salaryArray.map(item => ({x: item.date, y: item.salary})), salaryCanvas);
        drawChart(withdrawalArray.map(item => ({x: item.date, y: item.withdrawal})), withdrawalCanvas);
    });

    return(
        <React.Fragment>
            <h2>Статистика</h2>
            <div className="statistics">
                <div className="statistics-card">
                    <h3>Средняя оценка</h3>
                    <p className="date-range">{`${moment(props.ratings[0].date).format('DD.MM.gggg')} - ${moment(props.ratings[props.ratings.length - 1].date).format('DD.MM.gggg')}`}</p>
                    <canvas ref={ratingsCanvas} width="273px" height="80px">
                        Your browser don't support canvas.
                    </canvas>
                    <p className="value"><img src={arrowTopIcon} alt=""/>&nbsp;{middleRating}</p>
                    <span className="percent">25% more</span>
                </div>
                <div className="statistics-card">
                    <h3>Ты уже заработала</h3>
                    <p className="date-range">{`${moment(salaryArray[0].date).format('DD.MM.gggg')} - ${moment(salaryArray[salaryArray.length - 1].date).format('DD.MM.gggg')}`}</p>
                    <canvas ref={salaryCanvas} width="273px" height="80px">
                        Your browser don't support canvas.
                    </canvas>
                    <p className="value"><img src={arrowTopIcon} alt=""/>&nbsp;{middleWithdrawal}</p>
                    <span className="percent">25% more</span>
                </div>
                <div className="statistics-card">
                    <h3>Доступно на вывод</h3>
                    <p className="date-range">{`${moment(withdrawalArray[0].date).format('DD.MM.gggg')} - ${moment(withdrawalArray[withdrawalArray.length - 1].date).format('DD.MM.gggg')}`}</p>
                    <canvas ref={withdrawalCanvas} width="273px" height="80px">
                        Your browser don't support canvas.
                    </canvas>
                    <p className="value"><img src={arrowTopIcon} alt=""/>&nbsp;{middleSalary}</p>
                    <span className="percent">25% more</span>
                </div>
            </div>
        </React.Fragment>
    );
}

export default Statistics;