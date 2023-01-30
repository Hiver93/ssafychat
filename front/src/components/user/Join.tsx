import "../../styles/components/common/sign_in_up.css";
import TextBox from "../../widget/InputTextBox";

function Join(){
    return(
        <div className="login_join_container">
            <div className="login_join_logo_name draggable">Sign Up</div>
            <div>
                <div className="nomal_area">
                    <form className="login_text_box">
                        {/* 로그인 입력 박스 두개 */}
                        <TextBox item="id"></TextBox>
                        <TextBox item="pwd"></TextBox>
                        <TextBox item="ssafy_id"></TextBox>
                        <TextBox item="name"></TextBox>
                        <TextBox item="job"></TextBox>
                        <TextBox item="company"></TextBox>
                        {/* 로그인 버튼 */}
                        <input className="submit_btn_upper draggable" type="submit" value="Sign Up" />
                        <hr className="hr_tag" />
                        {/* 회원가입 버튼 */}
                        <button className="submit_btn_lower draggable" type="submit">Back</button>
                    </form>
                    <div className="login_footer draggable">
                        <p className="footer_text">ⓣTeam BlueBerryPie. All rights reserved.</p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Join;