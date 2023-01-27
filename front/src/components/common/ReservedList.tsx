import "../../styles/components/common/reserved-list.css";


function ReservedCard(props : any){
    return(
        <div className="reserved_card_outer">
            <div className="reserved_card">
                <div className="reserved_card_header">
                    멘토링
                    2022.02.02
                </div>
                <div className="reserved_card_content_container">
                    이름
                </div>
                <div className="reserved_card_content_container">
                    회사
                </div>
                <div className="reserved_card_content_container">
                    직무
                </div>
                <div className="reserved_card_content_container">
                    기수
                </div>
                <div className="reserved_card_button_container">
                   
                        <div className="reserved_card_button">
                            <div className="reserved_card_text">
                                입장하기
                            </div>
                        </div>                    
                   
                </div>
            </div>
        </div>
    )
}

function ReservedList(props : any){
    return (
    // 카드리스트 전체를 감싸는 컨테이너
    <div className="reserved_list_container">
        {/* 좌 화살표 */}
        <div className="reserved_list_arrow">
            <div className="arrow_button">
                <div className="arrow">&lt;</div>
            </div>
        </div>
        {/* 카드리스트와 헤더를 감싸는 컨테이너 */}
        <div className="reserved_list_inner_container">
            {/* 헤더 */}
           <div className="reserved_list_header">
                <div className="header_text">
                    멘토링 목록
                </div>
           </div>

            {/* 카드리스트 */}
            <div className="reserved_card_container">
                <ReservedCard></ReservedCard>
                <ReservedCard></ReservedCard>                
            </div>

        </div>

        {/* 우측 화살표 */}
        <div className="reserved_list_arrow">
        <div className="arrow_button">
            <div className="sam">
                &gt;
            </div>
        </div>
        </div>

        
    </div>
    )
}

export default ReservedList;