import "../../styles/components/mentoring/reserved-list.css"

function dragItem(event : any){
    // 요소 가져오기
    let elem = event.target;
    // 기존의 드래그를 없애기
    // elem.ondragstart = function(){
    //     return false;
    // }
    
    if(elem.className === "reserved_list_enter_button"){
        return;
    }

    // 드래그
    let onDrag = true;

    // 요소가 카드를 가리키게 하기
    while(elem.className !== "reserved_list_tr_body"){
        elem = elem.parentElement;        
    }
    console.log(elem)
    // 카드 아우터 요소를 가져오기
    const outer = elem.parentElement;
    console.log(outer);
    // 카드 컨테이너 요소를 가져오기
    const container = outer.parentElement;

    // 카드가 이동가능하게 만들기
    elem.style.position = 'absolute';
    elem.style.zIndex = 1000;

    // 카드의 기존 위치 저장
    const leftPos = elem.style.left;
    const topPos = elem.style.top;

    // 카드를 body의 자식으로
    // document.body.append(elem);

    // 드래그 위치에 따라 위치를 바꾸는 함수
    function moveAt(pageX : number,pageY : number){
        elem.style.left = pageX - elem.offsetWidth / 2 + 'px';
        elem.style.top = pageY - elem.offsetHeight / 2 + 'px';
    }

    // 처음 클릭했을 때 마우스위치로 이동
    moveAt(event.pageX, event.pageY);

    

    // 마우스가 움직이면 카드 위치 변경하게 할 함수
    function onMouseMove(event : any){
        // 
        moveAt(event.pageX,event.pageY);
    }

    // 마우스가 움직이면 함수 호출
    document.addEventListener('mousemove', onMouseMove);

    container.onmouseleave = function(){
        if(onDrag){
            // **********************
            // 이 곳에 기능을 넣어야 함
            // **********************
            alert("삭제합니다");
            onDrag = false;
        }
    }
    container.onmouseover = function(){
        onDrag = false;
    }

    // 마우스클릭 해제시 원래대로
    elem.onmouseup = function(){
        document.removeEventListener('mousemove',onMouseMove);
        elem.onmouseup = null;
        outer.append(elem);
        elem.style.left = leftPos;
        elem.style.top = topPos;
        elem.style.zIndex = 'auto';
        elem.style.left = 'auto';
        elem.style.top = 'auto';
        elem.style.display = 'table-row';
        console.log(elem.parentElement);
    }
}


function ReservedListItem(props : any){
    return(
        <tr className="reserved_list_tr_body" onMouseDown={(event)=>{
            dragItem(event);
        }}>
            <td>2023.01.01</td>
            <td>김도원</td>
            <td>7전8기</td>
            <td>ssafy@ssafy.com</td>
            <td><div className="reserved_list_enter_button" onClick={(event)=>{
                props.func(event);
            }} >입장</div></td>
        </tr>
    )
}

function enterMeeting(event : any){
    alert('입장합니다.');
}

function ReservedList(props : any){
    return (
        <div className="reserved_list_container">
            <div className="reserved_list_header">
                나는 헤더
            </div>
            <table className="reserved_list_table">
                <thead>
                    <tr className="reserved_list_tr_head">
                       <th>날짜</th>
                       <th>이름</th>
                       <th>타이틀</th>
                       <th>이메일</th>
                       <th></th>
                    </tr>
                </thead>
                <tbody className="reserved_list_tbody">
                    <ReservedListItem func={(event : any)=>{
                        enterMeeting(event);
                    }}></ReservedListItem>
                </tbody>
            </table>
        </div>
    )
}

export default ReservedList;